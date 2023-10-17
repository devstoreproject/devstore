package project.main.webstore.domain.users.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.enums.ProviderId;
import project.main.webstore.domain.users.enums.UserStatus;
import project.main.webstore.domain.users.exception.UserExceptionCode;
import project.main.webstore.domain.users.repository.UserRepository;
import project.main.webstore.email.enums.CheckCondition;
import project.main.webstore.email.event.UserRegistrationApplicationEvent;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.redis.RedisUtils;
import project.main.webstore.utils.FileUploader;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;
    private final FileUploader fileUploader;
    private final RedisUtils redisUtils;

    public User postUser(User user, ImageInfoDto imageInfo) {
        //TODO : admin을 직접 입명하는 것 필요 즉 admin이 직접 admin의 권한을 부여하는 것이 필요하다. 구상은 admin이 계정 중 직접 admin을 부여하는 것을 고려 중
        verifyExistsEmail(user.getEmail());
        setEncryptedPassword(user);
        saveProfileImageIfHas(user, imageInfo);
        publisher.publishEvent(new UserRegistrationApplicationEvent(this, user, CheckCondition.JOIN));
        return userRepository.save(user);

    }

    public User oAuth2CreateOrGet(User user) {
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());
        if (findUser.isPresent()) {
            User savedUser = findUser.get();
            switch (savedUser.getUserStatus()) {
                case ACTIVE:
                case SLEEP:
                    if (isProvider(user, savedUser)) {
                        //변경사항 확인후 저장
                        changeInfoToOAuthUser(user, savedUser);
                        return savedUser;
                    } else {
                        throw new BusinessLogicException(UserExceptionCode.USER_JWT_EXIST);
                    }
                case DELETE:    //삭제 상태
                    changeInfoToOAuthUser(user, savedUser); //값이 변경된다.
                    return savedUser;
            }
        } else if(findUser.isEmpty()) {
            setEncryptedPassword(user);
            return userRepository.save(user);
        }
        return findUser.get();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getUser(Long userId) {
        return validUser(userId);
    }

    public Page<User> getUserPage(Pageable pageable) {
        Page<User> userPage = userRepository.findUserPage(pageable);
        return userPage;
    }

    public User patchUser(User user, ImageInfoDto imageInfo) {
        User findUser = validUser(user.getId());
        if (findUser.getProviderId() != ProviderId.JWT) { //소셜 로그인 검증
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_JWT);
        }
        Optional.ofNullable(user.getUserStatus()).ifPresent(findUser::setUserStatus);
        Optional.ofNullable(user.getGrade()).ifPresent(findUser::setGrade);
        Optional.ofNullable(user.getPhone()).ifPresent(findUser::setPhone);
        Optional.ofNullable(user.getNickName()).ifPresent(findUser::setNickName);
        Optional.ofNullable(user.getPassword()).ifPresent(password -> findUser.setPassword(passwordEncoder.encode(user.getPassword())));
        saveProfileImageIfHas(user, imageInfo);

        return findUser;
    }

    public boolean checkNickName(String nick) {
        Optional<User> find = userRepository.findByNickName(nick);
        if (find.isPresent()) {
            throw new BusinessLogicException(UserExceptionCode.USER_EXIST);
        }
        return true;
    }

    public User authMail(String key) {
        Object byKey = redisUtils.findByKey(key);
        String email = Optional.ofNullable((String) redisUtils.findByKey(key)).orElseThrow(() -> new BusinessLogicException(UserExceptionCode.USER_MAIL_TIME_OUT));

        User user = validUserByEmail(email);
        user.setUserStatus(UserStatus.ACTIVE);
        return user;
    }

    /*
     * 회원이 존재 하면 예외 발생
     * */
    private void verifyExistsEmail(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()) {
            throw new BusinessLogicException(UserExceptionCode.USER_EXIST);
        }
    }

    private void setEncryptedPassword(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
    }

    /*
     * 회원이 없으명 예외 발생
     * */
    // 내부 동작 메서드 //
    private User validUser(Long userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(UserExceptionCode.USER_NOT_FOUND));
        log.info("### userId = {}", findUser.getId());
        return findUser;
    }

    private User validUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new BusinessLogicException(UserExceptionCode.USER_NOT_FOUND));
    }


    private boolean isProvider(User user, User findUser) {
        return findUser.getProviderId().equals(user.getProviderId());
    }

    private void changeInfoToOAuthUser(User user, User findUser) {
        findUser.setProviderId(user.getProviderId());
        findUser.setProfileImage(user.getProfileImage());
        findUser.setPassword(user.getPassword());
        findUser.setUserStatus(UserStatus.ACTIVE);
    }

    private void saveProfileImageIfHas(User user, ImageInfoDto imageInfo) {
        if (imageInfo != null) {
            Image image = fileUploader.uploadImage(imageInfo);
            user.setProfileImage(image.getImagePath());
        }
    }

    public String getTmpPassword(User user) {
        Optional<User> findUser = userRepository.findByEmailAndAndUserNameAndPhone(user.getEmail(), user.getUserName(), user.getPhone());
        User savedUser = findUser.orElseThrow(() -> new BusinessLogicException(UserExceptionCode.USER_NOT_FOUND));
        if (!savedUser.getProviderId().equals(ProviderId.JWT)) {
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_JWT);
        }
        String tmpPassword = RandomStringUtils.randomAlphanumeric(8);
        String encodedPassword = passwordEncoder.encode(tmpPassword);
        savedUser.setPassword(encodedPassword);
        return tmpPassword;
    }

    public void transActive(String username, String password) {
        User user = validUserByEmail(username);
        String encode = passwordEncoder.encode(password);
        if (!user.getPassword().equals(encode)) {
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_FOUND);
        }
        publisher.publishEvent(new UserRegistrationApplicationEvent(this, user, CheckCondition.ACTIVE));


    }
}
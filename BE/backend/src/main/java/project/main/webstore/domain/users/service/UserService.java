package project.main.webstore.domain.users.service;

import lombok.RequiredArgsConstructor;
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
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.domain.users.enums.UserStatus;
import project.main.webstore.domain.users.exception.UserExceptionCode;
import project.main.webstore.domain.users.repository.UserRepository;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.utils.FileUploader;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher publisher;
    private final FileUploader fileUploader;

    public User postUser(User user, ImageInfoDto imageInfo) {
        verifyExistsEmail(user.getEmail());
        setEncryptedPassword(user);

        saveProfileImage(user, imageInfo);

        User savedUser = userRepository.save(user);

        //이벤트 발급필요

        return savedUser;
    }

    public User oAuth2CreateOrGet(User user) {

        Optional<User> findUser = userRepository.findByEmail(user.getEmail());

        if (findUser.isPresent()) {
            User savedUser = findUser.get();
            switch (savedUser.getUserStatus()) {
                case ACTIVE:
                case SLEEP:
                    if (isProvider(user, savedUser)) {
                        return savedUser;
                    } else {
                        throw new BusinessLogicException(UserExceptionCode.USER_JWT_EXIST);
                    }
                case DELETE:    //삭제 상태
                    changeInfoToOAuthUser(user, savedUser); //값이 변경된다.
                    return savedUser;
            }
        }
        setEncryptedPassword(user);
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getUser(Long userId) {
        return validUserExist(userId);
    }

    public Page<User> getUserPage(Long userId, Pageable pageable) {
        User user = validUserExist(userId);
        if (user.getUserRole() != UserRole.ADMIN) {
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_ACCESS);
        }
        Page<User> userPage = userRepository.findUserPage(pageable);
        return userPage;
    }

    //사진이 없을 때 PatchUser
    public User patchUser(User user, ImageInfoDto imageInfo) {
        User findUser = validUserExist(user.getId());
        if (findUser.getProviderId() != ProviderId.JWT) { //소셜 로그인 검증
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_JWT);
        }
        Optional.ofNullable(user.getUserStatus()).ifPresent(findUser::setUserStatus);
        Optional.ofNullable(user.getGrade()).ifPresent(findUser::setGrade);
        Optional.ofNullable(user.getAddress()).ifPresent(findUser::setAddress);
        Optional.ofNullable(user.getNickName()).ifPresent(findUser::setNickName);

        saveProfileImage(user, imageInfo);

        return findUser;
    }


    /*
     * 회원이 존재 하면 예외 발생
     * */
    private User verifyExistsEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new BusinessLogicException(UserExceptionCode.USER_EXIST));
    }

    private void setEncryptedPassword(User user) {
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
    }

    /*
     * 회원이 없으명 예외 발생
     * */
    // 내부 동작 메서드 //
    public User validUserExist(Long UserId) {
        return userRepository.findById(UserId).orElseThrow(() -> new BusinessLogicException(UserExceptionCode.USER_NOT_FOUND));
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

    private void saveProfileImage(User user, ImageInfoDto imageInfo) {
        if (imageInfo != null) {
            Image image = fileUploader.uploadImage(imageInfo);
            user.setProfileImage(image.getImagePath());
        }
    }
}

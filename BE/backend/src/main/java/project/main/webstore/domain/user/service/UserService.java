package project.main.webstore.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.user.entity.User;
import project.main.webstore.domain.user.enums.Status;
import project.main.webstore.domain.user.repository.UserRepository;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;
import project.main.webstore.security.mail.ConfirmationToken;
import project.main.webstore.security.mail.ConfirmationTokenService;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    // User 생성
    public User createUser(User user) throws MessagingException { // Exception 추가?
        validateDuplicateUser(user.getEmail()); // Email 중복 확인

        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        User createdUser = userRepository.save(user);

        Long id = user.getId();
        Optional<String> optional = Optional.ofNullable(user.getNickName());
        if (optional.isEmpty()) {
            user.setNickName("user"+id);
        }

        String profileURI = "https://source.boringavatars.com/beam/120/" + id + "?colors=66FFFF,8CBFE6,B380CC,D940B3,FF0099";
        user.setProfileImage(profileURI);

        userRepository.save(user);

        confirmationTokenService.createEmailConfirmationToken(user.getId(), user.getEmail());

        return createdUser;
    }

    // User 수정
    public User updateUser(User user) {
        // Id를 통해 User 확인
        User findUser = verifiedUser(user.getId());

        // nickName 수정
        Optional.ofNullable(user.getNickName())
                .ifPresent(findUser::setNickName);
        // profileImage 수정
        // ToDO: S3 이미지 저장 로직 추가
        Optional.ofNullable(user.getProfileImage())
                .ifPresent(findUser::setProfileImage);

        // ToDo: 패스워드 수정

        return userRepository.save(user);
    }

    // User 삭제
    public void deleteUser(Long id) {
        User user = verifiedUser(id);
        verifiedDeleteUser(user);
        user.setStatus(Status.DELETE);
    }

    // User 조회
    @Transactional(readOnly = true)
    public User getUser(Long id) {
        Optional<User> optional = userRepository.findById(id);
        // ToDO: DeletedUser 조회 불가하도록 조치
        return optional.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.USERS_NOT_VALID));
    }

    // Email 중복 검증
    private void validateDuplicateUser(String email) {
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            throw new BusinessLogicException(CommonExceptionCode.USER_EMAIL_EXIST);
        }
    }

    // Email 확인
    public void confirmEmail(String token) {
        ConfirmationToken findConfirmationToken = confirmationTokenService.findByIdAndExpired(token);
        Optional<User> optionalUser = userRepository.findById(findConfirmationToken.getId());

        User user = optionalUser.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.USERS_NOT_VALID));

        confirmationTokenService.useToken(findConfirmationToken);
        user.setEmailVerified(true);
        userRepository.save(user);
    }

    private User verifiedUser(Long id) {
        Optional<User> optional = userRepository.findById(id);

        return optional.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.USERS_NOT_VALID));
    }

    private static void verifiedDeleteUser(User user) {
        int status = user.getStatus().getNum();
        if (status == 3) {
            throw new BusinessLogicException(CommonExceptionCode.DELETED_USER);
        }
    }
}

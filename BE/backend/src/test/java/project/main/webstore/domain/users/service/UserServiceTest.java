package project.main.webstore.domain.users.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static project.main.webstore.domain.users.exception.UserExceptionCode.USER_NOT_FOUND;
import static project.main.webstore.domain.users.exception.UserExceptionCode.USER_NOT_JWT;

import java.util.Optional;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.enums.ProviderId;
import project.main.webstore.domain.users.enums.UserStatus;
import project.main.webstore.domain.users.exception.UserExceptionCode;
import project.main.webstore.domain.users.repository.UserRepository;
import project.main.webstore.domain.users.stub.UserStub;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.redis.RedisUtils;
import project.main.webstore.stub.ImageStub;
import project.main.webstore.utils.FileUploader;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService service;
    UserStub userStub = new UserStub();
    ImageStub imageStub = new ImageStub();
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private FileUploader fileUploader;
    @Mock
    private RedisUtils redisUtils;

    @Test
    @DisplayName("사용자 등록")
    void post_user_test() throws Exception {
        // given
        User post = userStub.createUser(null);
        ImageInfoDto postImage = imageStub.createImageInfoDto(1, true);
        Image saveImage = imageStub.createImage(1L, 1, true);
        User savedUser = userStub.createUser(1L);
        savedUser.setProfileImage(saveImage.getImagePath());

        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(passwordEncoder.encode(anyString())).willReturn(UUID.randomUUID().toString());
        given(fileUploader.uploadImage(any(ImageInfoDto.class))).willReturn(saveImage);
        willDoNothing().given(publisher).publishEvent(any(ApplicationEvent.class));
        given(userRepository.save(any(User.class))).willReturn(savedUser);
        // when
        User result = service.postUser(post, postImage);
        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(savedUser);
    }

    @Test
    @DisplayName("사용자 등록 : 실패[존재 하는 이메일]")
    void post_user_fail_test() throws Exception {
        // given
        User post = userStub.createUser(null);
        ImageInfoDto postImage = imageStub.createImageInfoDto(1, true);
        Image saveImage = imageStub.createImage(1L, 1, true);
        User savedUser = userStub.createUser(1L);
        savedUser.setProfileImage(saveImage.getImagePath());

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(savedUser));
        // when
        Assertions.assertThatThrownBy(() -> service.postUser(post, postImage)).isInstanceOf(
                BusinessLogicException.class).hasMessage(UserExceptionCode.USER_EXIST.getMessage());
    }

    @Test
    @DisplayName("OAuth2 생성 or 로그인 테스트")
    void oauth2_create_test() throws Exception {
        // given
        User post = userStub.createUser(null);
        User savedUser = userStub.createUser(1L);
        savedUser.setProviderId(ProviderId.GOOGLE);

        given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());
        given(passwordEncoder.encode(anyString())).willReturn(UUID.randomUUID().toString());
        given(userRepository.save(any(User.class))).willReturn(savedUser);
        // when
        User result = service.oAuth2CreateOrGet(post);
        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(savedUser);
    }

    @Test
    @DisplayName("OAuth2 생성 기존의 계정 정보 변경 테스트")
    void oauth2_change_test() throws Exception {
        // given
        User post = userStub.createUser(null);
        post.setProviderId(ProviderId.GOOGLE);
        User savedUser = userStub.createUser(1L);
        savedUser.setProviderId(ProviderId.GOOGLE);
        savedUser.setUserStatus(UserStatus.ACTIVE);

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(savedUser));
        // when
        User result = service.oAuth2CreateOrGet(post);
        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(savedUser);
    }

    @Test
    @DisplayName("OAuth2 생성 기존의 계정 정보 변경 테스트")
    void oauth2_change_delete_id_test() throws Exception {
        // given
        User post = userStub.createUser(null);
        post.setProviderId(ProviderId.GOOGLE);
        User savedUser = userStub.createUser(1L);
        savedUser.setProviderId(ProviderId.GOOGLE);
        savedUser.setUserStatus(UserStatus.DELETE);

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(savedUser));
        // when
        User result = service.oAuth2CreateOrGet(post);
        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(savedUser);
    }

    @Test
    @DisplayName("OAuth2 생성 기존의 계정 정보 변경 테스트")
    void oauth2_change_sleep_id_test() throws Exception {
        // given
        User post = userStub.createUser(null);
        post.setProviderId(ProviderId.GOOGLE);
        User savedUser = userStub.createUser(1L);
        savedUser.setProviderId(ProviderId.GOOGLE);
        savedUser.setUserStatus(UserStatus.SLEEP);

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(savedUser));
        // when
        User result = service.oAuth2CreateOrGet(post);
        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(savedUser);
    }

    @Test
    @DisplayName("OAuth2 생성 기존의 계정 정보 변경 테스트")
    void oauth2_create_fail_test() throws Exception {
        // given
        User post = userStub.createUser(null);
        post.setProviderId(ProviderId.GOOGLE);
        User savedUser = userStub.createUser(1L);
        savedUser.setProviderId(ProviderId.JWT);
        savedUser.setUserStatus(UserStatus.ACTIVE);

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(savedUser));
        // when
        Assertions.assertThatThrownBy(() -> service.oAuth2CreateOrGet(post)).isInstanceOf(
                        BusinessLogicException.class)
                .hasMessage(UserExceptionCode.USER_JWT_EXIST.getMessage());
    }

    @Test
    @DisplayName("사용자 제거 테스트")
    void delete_user_test() throws Exception {
        // given
        willDoNothing().given(userRepository).deleteById(anyLong());
        // when
        service.deleteUser(1L);
        // then
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("사용자 정보 단건 조회")
    void get_user_test() throws Exception {
        User savedUser = userStub.createUser(1L);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(savedUser));

        User result = service.getUser(1L);

        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(savedUser);
    }

    @Test
    @DisplayName("사용자 정보 단건 조회 : 실패")
    void get_user_fail_test() throws Exception {
        User savedUser = userStub.createUser(1L);
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.getUser(1l))
                .hasMessage(USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("사용자 정보 전체 조회")
    void get_all_user_test() throws Exception {
        Page<User> pageEntity = userStub.userPage();
        given(userRepository.findUserPage(any(Pageable.class))).willReturn(pageEntity);

        Page<User> result = service.getUserPage(userStub.getPage());

        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(pageEntity);
    }

    @Test
    @DisplayName("사용자 정보 수정 : 실패 [소셜로그인 시용자]")
    void patch_user_fail_test() throws Exception {
        // given
        User savedUser = userStub.createUser(1L);
        savedUser.setProviderId(ProviderId.GOOGLE);

        User patch = userStub.createUser(1L);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(savedUser));
        // when
        Assertions.assertThatThrownBy(
                () -> service.patchUser(patch, imageStub.createImageInfoDto(1, true))).hasMessage(
                USER_NOT_JWT.getMessage());
    }

    @Test
    @DisplayName("사용자 정보 수정 : 성공")
    void patch_user_test() throws Exception {
        // given
        User savedUser = userStub.createUser(1L);
        Image saveImage = imageStub.createImage(1L, 1, true);

        ImageInfoDto patchImage = imageStub.createImageInfoPath(1L, 1, true);
        User patch = userStub.createUser(1L);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(savedUser));
        given(fileUploader.uploadImage(any(ImageInfoDto.class))).willReturn(saveImage);
        // when
        User result = service.patchUser(patch, patchImage);

        Assertions.assertThat(result.getUserName()).isEqualTo(patch.getUserName());
        Assertions.assertThat(result.getName()).isEqualTo(patch.getName());
    }

    @Test
    @DisplayName("닉네임 검증 테스트 : 성공 가입한 회원 없음")
    void nickname_valid_test() throws Exception {
        // given
        given(userRepository.findByNickName(anyString())).willReturn(Optional.empty());
        // when
        boolean result = service.checkNickName("관리자");
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    @DisplayName("닉네임 검증 테스트 : 가입한 회원 존재")
    void nickname_valid_fail_test() throws Exception {
        // given
        given(userRepository.findByNickName(anyString())).willReturn(
                Optional.of(userStub.createUser(1L)));
        // when
        assertThatThrownBy(() -> service.checkNickName("관리자")).hasMessage(
                UserExceptionCode.USER_EXIST.getMessage());
        // then
    }

    @Test
    @DisplayName("메일 인증 성공")
    void auth_mail_test() throws Exception {
        // given
        User savedUser = userStub.createUser(1L);

        String key = "admin@gmail.com";
        given(redisUtils.findByKey(anyString())).willReturn(key);
        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(savedUser));
        // when
        User result = service.authMail(key);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(savedUser);
    }

    @Test
    @DisplayName("메일 인증 실패")
    void auth_mail_fail_test() throws Exception {
        // given
        String key = "admin@gmail.com";
        given(redisUtils.findByKey(anyString())).willReturn(null);
        // when
        assertThatThrownBy(() -> service.authMail(key)).hasMessage(
                UserExceptionCode.USER_MAIL_TIME_OUT.getMessage());
    }

    @Test
    @DisplayName("임시 비밀 번호 발급")
    void get_tmp_password_test() throws Exception {
        // given
        User savedUser = userStub.createUser(1L);
        User request = userStub.createUser(null);
        String newPasswordMock = UUID.randomUUID().toString();

        given(userRepository.findByEmailAndAndUserNameAndPhone(anyString(), anyString(),
                anyString())).willReturn(Optional.of(savedUser));
        given(passwordEncoder.encode(anyString())).willReturn(newPasswordMock);
        // when
        String password = service.getTmpPassword(request);

        Assertions.assertThat(password).isInstanceOf(String.class);
    }

    @Test
    @DisplayName("임시 비밀 번호 발급 실패 : 회원 조회 실패")
    void get_tmp_password_fail_no_user_test() throws Exception {
        // given
        User request = userStub.createUser(null);

        given(userRepository.findByEmailAndAndUserNameAndPhone(anyString(), anyString(),
                anyString())).willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.getTmpPassword(request)).hasMessage(
                USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("임시 비밀 번호 발급 실패 : JWT 회원아님")
    void get_tmp_password_fail_no_jwt_test() throws Exception {
        // given
        User request = userStub.createUser(null);
        User savedUser = userStub.createUser(1L);
        savedUser.setProviderId(ProviderId.GOOGLE);

        given(userRepository.findByEmailAndAndUserNameAndPhone(anyString(), anyString(),
                anyString())).willReturn(Optional.of(savedUser));

        Assertions.assertThatThrownBy(() -> service.getTmpPassword(request)).hasMessage(
                USER_NOT_JWT.getMessage());
    }

    @Test
    @DisplayName("휴면 회원 활성화 테스트")
    void trans_active_test() throws Exception {
        // given
        User request = userStub.createUser(null);
        User savedUser = userStub.createUser(1L);
        String username = "admin@gmail.com";
        String rawPassword = "admin11!!!";
        String password = UUID.randomUUID().toString();
        savedUser.setPassword(password);

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(savedUser));
        given(passwordEncoder.encode(anyString())).willReturn(password);
        willDoNothing().given(publisher).publishEvent(any(ApplicationEvent.class));

        service.transActive(username, rawPassword);

        verify(userRepository, times(1)).findByEmail(username);
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }

    @Test
    @DisplayName("휴면 회원 활성화 실패 테스트 : 비밀번호 오류")
    void trans_active_fail_test() throws Exception {
        // given
        User savedUser = userStub.createUser(1L);
        String username = "admin@gmail.com";
        String rawPassword = "admin11!!!";
        String password = UUID.randomUUID().toString();

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(savedUser));
        given(passwordEncoder.encode(anyString())).willReturn(password);

        Assertions.assertThatThrownBy(() -> service.transActive(username, rawPassword)).hasMessage(
                USER_NOT_FOUND.getMessage());
    }


}
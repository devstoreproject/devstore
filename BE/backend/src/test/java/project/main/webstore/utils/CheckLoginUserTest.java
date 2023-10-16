package project.main.webstore.utils;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.domain.users.exception.UserExceptionCode;
import project.main.webstore.security.dto.UserInfoDto;

class CheckLoginUserTest {

    @Test
    @DisplayName("로그인 회원 관리자 여부 체크 : 관리자 아님")
    void login_admin_test() throws Exception {
        // given
        String principal = "anonymousUser";
        // when
        // then
        Assertions.assertThatThrownBy(() -> CheckLoginUser.validAdmin(principal))
                .hasMessage(UserExceptionCode.USER_NOT_LOGIN.getMessage());
    }

    @Test
    @DisplayName("로그인 회원 관리자 여부 체크")
    void login_admin_no_admin_test() throws Exception {
        // given
        UserInfoDto principal = new UserInfoDto("1", "client@gmail.com", "김복자", UserRole.CLIENT);
        // when
        // then
        Assertions.assertThatThrownBy(() -> CheckLoginUser.validAdmin(principal))
                .hasMessage(UserExceptionCode.NOT_ADMIN.getMessage());
    }

    @Test
    @DisplayName("사용자id 반환 테스트")
    void get_login_id_fail_test() throws Exception{
        // given
        String principal = "anonymousUser";
        // when
        Long result = CheckLoginUser.getContextIdx(principal);
        // then
        Assertions.assertThat(result).isEqualTo(-1L);
    }

    @Test
    @DisplayName("사용자id 반환 테스트")
    void get_login_id_test() throws Exception{
        // given
        UserInfoDto principal = new UserInfoDto(1L, "client@gmail.com", "김복자", UserRole.CLIENT);
        // when
        Long result = CheckLoginUser.getContextIdx(principal);
        // then
        Assertions.assertThat(result).isEqualTo(1L);
    }


}
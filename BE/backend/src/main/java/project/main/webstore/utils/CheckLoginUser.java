package project.main.webstore.utils;


import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.domain.users.exception.UserExceptionCode;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.security.dto.UserInfoDto;

public class CheckLoginUser {

    public static void validUserSame(Object principal, Long userId){
        if(principal instanceof String){
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_LOGIN);
        }
        UserInfoDto info = (UserInfoDto) principal;
        if(info.getUserId() != userId){
            throw new BusinessLogicException(UserExceptionCode.USER_INFO_MISMATCH);
        }
    }
    public static void validAdmin(Object principal){
        if(principal instanceof String){
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_LOGIN);
        }
        UserInfoDto info = (UserInfoDto) principal;
        if(!info.getUserRole().equals(UserRole.ADMIN)){
            throw new BusinessLogicException(UserExceptionCode.NOT_ADMIN);
        }
    }


    public static Long getContextIdx(Object principal) {
        if (principal instanceof String) {
            return -1L;
        } else {
            return ((UserInfoDto) principal).getUserId();
        }
    }
    public static Long getContextIdAdminZero(Object principal) {
        if (principal instanceof String) {
            return -1L;
        } else {
            UserInfoDto info = (UserInfoDto) principal;

            if (info.getUserRole().equals(UserRole.ADMIN)) {
                return 0L;
            } else {
                return info.getUserId();
            }
        }
    }

}

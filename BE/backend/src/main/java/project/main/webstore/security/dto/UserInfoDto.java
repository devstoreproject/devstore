package project.main.webstore.security.dto;

import lombok.Getter;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.enums.UserRole;

@Getter
public class UserInfoDto {
    private String userIdStr;
    private Long userId;
    private String email;
    private String nickName;
    private UserRole userRole;

    public UserInfoDto(User user) {
        this.userIdStr =  String.valueOf(user.getId());
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.userRole = user.getUserRole();
    }

    public UserInfoDto(String userIdStr, String email, String nickName, UserRole userRole) {
        this.userIdStr = userIdStr;
        this.email = email;
        this.nickName = nickName;
        this.userRole = userRole;
    }

    public UserInfoDto(Long userId, String email, String nickName, UserRole userRole) {
        this.userId = userId;
        this.email = email;
        this.nickName = nickName;
        this.userRole = userRole;
    }
}

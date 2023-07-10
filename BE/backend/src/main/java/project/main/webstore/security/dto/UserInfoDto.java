package project.main.webstore.security.dto;

import lombok.Getter;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.enums.UserRole;

@Getter
public class UserInfoDto {
    private Long userId;
    private String email;
    private String nickName;
    private UserRole userRole;

    public UserInfoDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.userRole = user.getUserRole();
    }
}

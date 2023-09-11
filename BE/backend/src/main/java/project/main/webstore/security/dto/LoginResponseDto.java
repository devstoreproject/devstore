package project.main.webstore.security.dto;

import lombok.Getter;
import project.main.webstore.domain.users.entity.User;

@Getter
public class LoginResponseDto {
    private Long userId;
    private String email;
    private String profileImage;

    public LoginResponseDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.profileImage = user.getProfileImage();
    }
}

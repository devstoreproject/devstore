package project.main.webstore.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.main.webstore.domain.users.entity.User;

@Getter
@Builder
@AllArgsConstructor
public class UserGetResponseDto {
    private Long userId;
    private String email;
    private String password;
    private String nickname;
    private String profileImage;
    private String phone;
    private String username;

    public UserGetResponseDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickName();
        this.profileImage = user.getProfileImage();
        this.phone = user.getPhone();
        this.username = user.getUserName();
    }
}

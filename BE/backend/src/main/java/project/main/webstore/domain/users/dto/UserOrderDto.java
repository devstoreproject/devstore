package project.main.webstore.domain.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.users.entity.User;

@Getter
@NoArgsConstructor
public class UserOrderDto {
    private Long id;
    private String nickName;
    private String profileImage;
    private String email;

    public UserOrderDto(User user) {
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.profileImage = user.getProfileImage();
        this.email = user.getEmail();
    }
}

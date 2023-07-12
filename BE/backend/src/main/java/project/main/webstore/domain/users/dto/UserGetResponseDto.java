package project.main.webstore.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.valueObject.Address;

@Getter
@AllArgsConstructor
public class UserGetResponseDto {
    private Long userId;
    private String email;
    private String password;
    private String nickname;
    private String profileImage;
    private Address address;

    public UserGetResponseDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickName();
        this.profileImage = user.getProfileImage();
        this.address = user.getAddress();
    }
}
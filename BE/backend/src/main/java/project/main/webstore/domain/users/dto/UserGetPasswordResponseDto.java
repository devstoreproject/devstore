package project.main.webstore.domain.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserGetPasswordResponseDto {
    private String password;

    public UserGetPasswordResponseDto(String password) {
        this.password = password;
    }
}

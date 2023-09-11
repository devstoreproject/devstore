package project.main.webstore.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserGetPasswordRequestDto {
    private String email;
    private String name;
    private String phone;
}

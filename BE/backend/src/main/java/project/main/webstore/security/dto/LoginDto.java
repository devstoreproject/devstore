package project.main.webstore.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginDto {
    private String username;
    @Setter
    private String password;
}

package project.main.webstore.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "로그인 위한 스키마")
public class LoginDto {
    @Schema(example = "admin001@gmail.com")
    private String username;
    @Setter
    @Schema(example = "admin111")
    private String password;
}

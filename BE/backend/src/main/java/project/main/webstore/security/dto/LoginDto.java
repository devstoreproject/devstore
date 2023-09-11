package project.main.webstore.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "로그인 위한 스키마")
public class LoginDto {
    @Schema(example = "admin001@gmail.com")
    @NotNull
    private String username;
    @Setter
    @NotNull
    @Schema(example = "admin111")
    private String password;
}

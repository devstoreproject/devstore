package project.main.webstore.domain.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "데이터 전달 시 application/json 형식으로 전달해줘야한다.")
public class UserPostRequestDto {
    @NotBlank
    @Email
    @Schema(description = "이메일",example = "amdin@gmail.com")
    private String email;
    //패스워드 검증용 필드 필요
    @NotBlank
    @Schema(description = "비밀번호는 암호화 되어 보관됨",example = "admin123!!!qad")
    private String password;
    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    @Schema(description = "닉네임",example = "방성모씨의백엔드")
    private String nickname;
    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
            message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
    @Schema(description = "전화번호")
    private String phone;
    @Schema(description = "사용자 이름",example = "김복자")
    private String userName;

}

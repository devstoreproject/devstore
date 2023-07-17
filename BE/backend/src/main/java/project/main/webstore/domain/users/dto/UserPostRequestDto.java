package project.main.webstore.domain.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
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
    @Schema(description = "우편주소",example = "540740")
    private String zipCode;
    @Schema(description = "간단 주소 정보",example = "간단한 주소정보")
    private String addressSimple;
    @Schema(description = "주소 정보",example = "서울특별시 동작구 문래동 김순아파트")
    private String addressDetail;

}

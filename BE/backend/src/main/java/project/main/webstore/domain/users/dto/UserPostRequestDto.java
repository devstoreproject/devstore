package project.main.webstore.domain.users.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageSortDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
public class UserPostRequestDto {
    @NotBlank
    @Email
    private String email;
    //패스워드 검증용 필드 필요
    @NotBlank
    private String password;
    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    private String nickname;
    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
            message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
    private String phone;

    private String zipCode;
    private String addressSimple;
    private String addressDetail;

    private ImageSortDto imageInfo;
}

package project.main.webstore.domain.order.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.*;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderFormPatchDto {
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z가-힣]*$")
    @Size(min = 2, max = 10, message = "올바른 형식의 이름을 입력하세요")
    private String recipient;

    @NotEmpty
    @Pattern(regexp = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", message = "올바른 형식을 입력하세요")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 형식의 전화전호를 입력하세요")
    private String mobileNumber;

    @NotEmpty
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 형식의 전화번호를 입력하세요")
    private String homeNumber;

    @NotEmpty
    @Pattern(regexp = "^\\d{3}-\\d{2}$", message = "올바른 형식의 우편번호를 입력하세요")
    private String zipCode;

    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$", message = "올바른 형식의 주소를 입력하세요")
    private String addressSimple;

    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$", message = "올바른 형식의 주소를 입력하세요")
    private String addressDetail;

    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$", message = "특수문자 제외 20자 이내로 작성하세요")
    @Size(min = 0, max = 20)
    private String message;

    @Builder
    public OrderFormPatchDto(String recipient, String mobileNumber, String homeNumber,
                             String zipCode, String addressSimple, String addressDetail, String message) {
        this.recipient     = recipient;
        this.mobileNumber  = mobileNumber;
        this.homeNumber    = homeNumber;
        this.zipCode       = zipCode;
        this.addressSimple = addressSimple;
        this.addressDetail = addressDetail;
        this.message       = message;
    }
}

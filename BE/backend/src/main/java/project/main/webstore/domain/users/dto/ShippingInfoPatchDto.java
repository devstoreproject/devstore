package project.main.webstore.domain.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ShippingInfoPatchDto {
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

    @Builder
    public ShippingInfoPatchDto(String recipient, String email, String mobileNumber, String homeNumber,
                               String zipCode, String addressSimple, String addressDetail) {
        this.recipient = recipient;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.homeNumber = homeNumber;
        this.zipCode = zipCode;
        this.addressSimple = addressSimple;
        this.addressDetail = addressDetail;
    }
}

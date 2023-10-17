package project.main.webstore.domain.users.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShippingInfoPatchDto {
    @Pattern(regexp = "^[a-zA-Z가-힣]*$")
    @Size(min = 2, max = 10, message = "올바른 형식의 이름을 입력하세요")
    private String recipient;

    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "올바른 형식의 전화전호를 입력하세요")
    private String mobileNumber;

    @Pattern(regexp = "^\\d{3}-\\d{2}$", message = "올바른 형식의 우편번호를 입력하세요")
    private String zipCode;

    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$", message = "올바른 형식의 주소를 입력하세요")
    private String addressSimple;

    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$", message = "올바른 형식의 주소를 입력하세요")
    private String addressDetail;

    @Builder
    public ShippingInfoPatchDto(String recipient, String mobileNumber,
                               String zipCode, String addressSimple, String addressDetail) {
        this.recipient = recipient;
        this.mobileNumber = mobileNumber;
        this.zipCode = zipCode;
        this.addressSimple = addressSimple;
        this.addressDetail = addressDetail;
    }
}

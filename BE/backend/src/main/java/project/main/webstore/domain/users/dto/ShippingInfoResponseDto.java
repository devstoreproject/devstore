package project.main.webstore.domain.users.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.users.entity.ShippingInfo;

@Getter
@Setter
@NoArgsConstructor
public class ShippingInfoResponseDto {
    private Long infoId;
    private String recipient;
    private String mobileNumber;
    private String zipCode;
    private String addressSimple;
    private String addressDetail;


    @Builder(builderMethodName = "responseDto")
    public ShippingInfoResponseDto(ShippingInfo info) {
        this.infoId = info.getInfoId();
        this.recipient = info.getRecipient();
        this.mobileNumber = info.getAddress().getPhone();
        this.zipCode = info.getAddress().getZipCode();
        this.addressSimple = info.getAddress().getAddressSimple();
        this.addressDetail = info.getAddress().getAddressDetail();
    }
}

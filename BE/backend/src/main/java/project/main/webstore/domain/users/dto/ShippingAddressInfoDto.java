package project.main.webstore.domain.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.users.entity.ShippingInfo;

@Getter
@NoArgsConstructor
public class ShippingAddressInfoDto {
    private Long infoId;
    private String recipient; // 배송받는사람
    private String zipCode;
    private String addressSimple;
    private String addressDetail;
    private String phone;

    public ShippingAddressInfoDto(ShippingInfo info) {
        this.infoId = info.getInfoId();
        this.recipient = info.getRecipient();
        this.zipCode = info.getAddress().getZipCode();
        this.addressSimple = info.getAddress().getAddressSimple();
        this.addressDetail = info.getAddress().getAddressDetail();
        this.phone = info.getAddress().getPhone();
    }
}

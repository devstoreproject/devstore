package project.main.webstore.domain.users.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.valueObject.Address;

@Getter
@NoArgsConstructor
public class ShippingAddressInfoDto {
    private String recipient; // 배송받는사람
    private String zipCode;
    private String addressSimple;
    private String addressDetail;
    private String phone;

    public ShippingAddressInfoDto(Address address, String recipient) {
        this.recipient = recipient;
        this.zipCode = address.getZipCode();
        this.addressSimple = address.getAddressSimple();
        this.addressDetail = address.getAddressDetail();
        this.phone = address.getPhone();
    }
}

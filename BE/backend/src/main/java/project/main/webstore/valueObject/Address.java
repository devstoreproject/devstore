package project.main.webstore.valueObject;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.*;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Address {
    private String zipCode;
    private String addressSimple;
    private String addressDetail;
    private String phone;

    @Builder(buildMethodName = "addressTotalBuild" , builderMethodName = "addressTotalBuilder")
    public Address(String zipCode, String addressSimple, String addressDetail, String phone) {
        this.zipCode = zipCode;
        this.addressSimple = addressSimple;
        this.addressDetail = addressDetail;
        this.phone = phone;
    }
}

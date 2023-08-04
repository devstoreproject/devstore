package project.main.webstore.domain.users.mapper;

import org.springframework.stereotype.Component;
import project.main.webstore.domain.users.dto.ShippingInfoPatchDto;
import project.main.webstore.domain.users.dto.ShippingInfoPostDto;
import project.main.webstore.domain.users.dto.ShippingInfoResponseDto;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.valueObject.Address;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShippingMapper {

    public ShippingInfoResponseDto infoGetResponseDto(ShippingInfo info) {
        return new ShippingInfoResponseDto(info);
    }

    public ShippingInfo infoPostToInfo(ShippingInfoPostDto infoPostDto) {
        return ShippingInfo.builder()
                .recipient(infoPostDto.getRecipient())
                .address(new Address(infoPostDto.getZipCode(),infoPostDto.getAddressSimple(),infoPostDto.getAddressDetail(),infoPostDto.getMobileNumber()))
                .build();
    }

    public ShippingInfo infoPatchToInfo(ShippingInfoPatchDto infoPatchDto) {
        return ShippingInfo.builder()
                .recipient(infoPatchDto.getRecipient())
                .address(new Address(infoPatchDto.getZipCode(),infoPatchDto.getAddressSimple(),infoPatchDto.getAddressDetail(),infoPatchDto.getMobileNumber()))
                .build();
    }

    public ShippingInfoResponseDto infoToInfoResponseDto(ShippingInfo info) {
        return ShippingInfoResponseDto.responseDto().info(info).build();

    }

    public List<ShippingInfoResponseDto> InfoResponseDtoList(List<ShippingInfo> infoList) {
        return infoList.stream().map(shippingInfo -> new ShippingInfoResponseDto(shippingInfo)).collect(Collectors.toList());

    }

}

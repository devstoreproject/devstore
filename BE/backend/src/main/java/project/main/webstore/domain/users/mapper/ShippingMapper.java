package project.main.webstore.domain.users.mapper;

import org.springframework.stereotype.Component;
//import project.main.webstore.domain.order.dto.OrderFormResponseDto;
//import project.main.webstore.domain.order.entity.OrderForm;
import project.main.webstore.domain.users.dto.ShippingInfoPatchDto;
import project.main.webstore.domain.users.dto.ShippingInfoPostDto;
import project.main.webstore.domain.users.dto.ShippingInfoResponseDto;
import project.main.webstore.domain.users.entity.ShippingInfo;

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
                .email(infoPostDto.getEmail())
                .mobileNumber(infoPostDto.getMobileNumber())
                .homeNumber(infoPostDto.getHomeNumber())
                .zipCode(infoPostDto.getZipCode())
                .addressSimple(infoPostDto.getAddressSimple())
                .addressDetail(infoPostDto.getAddressDetail())
                .build();
    }

    public ShippingInfo infoPatchToInfo(ShippingInfoPatchDto infoPatchDto) {
        return ShippingInfo.builder()
                .recipient(infoPatchDto.getRecipient())
                .email(infoPatchDto.getEmail())
                .mobileNumber(infoPatchDto.getMobileNumber())
                .homeNumber(infoPatchDto.getHomeNumber())
                .zipCode(infoPatchDto.getZipCode())
                .addressSimple(infoPatchDto.getAddressSimple())
                .addressDetail(infoPatchDto.getAddressDetail())
                .build();
    }

    public ShippingInfoResponseDto infoToInfoResponseDto(ShippingInfo info) {
        return ShippingInfoResponseDto.responseDto().build();

    }

    public List<ShippingInfoResponseDto> InfoResponseDtoList(List<ShippingInfo> infoList) {
        return infoList.stream().map(shippingInfo -> new ShippingInfoResponseDto(shippingInfo)).collect(Collectors.toList());

    }

}

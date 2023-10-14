package project.main.webstore.domain.users.controller;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.main.webstore.domain.users.dto.ShippingInfoPatchDto;
import project.main.webstore.domain.users.dto.ShippingInfoPostDto;
import project.main.webstore.domain.users.dto.ShippingInfoResponseDto;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.mapper.ShippingMapper;
import project.main.webstore.domain.users.service.ShippingService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.CheckLoginUser;

@RestController
@RequestMapping("/api/address")
@Validated
@RequiredArgsConstructor
public class ShippingController {
    private final ShippingService service;
    private final ShippingMapper mapper;

    @PostMapping
    public ResponseEntity<ResponseDto<ShippingInfoResponseDto>> createInfo(@RequestBody @Valid ShippingInfoPostDto post,
                                     @Parameter(hidden = true) @AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        ShippingInfo info = mapper.infoPostToInfo(post);
        ShippingInfo writeInfo = service.postAddressInfo(info, userId);
        ShippingInfoResponseDto response = mapper.infoToInfoResponseDto(writeInfo);

        var responseDto = ResponseDto.<ShippingInfoResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{shipping-info-id}")
    public ResponseEntity<ResponseDto<ShippingInfoResponseDto>> patchShippingInfo(@PathVariable("shipping-info-id") @Positive Long shippingInfoId,
                                                                                  @Parameter(hidden = true)@AuthenticationPrincipal Object principal,
                                            @RequestBody @Valid ShippingInfoPatchDto patch) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        ShippingInfo info = mapper.infoPatchToInfo(patch);
        info.addId(shippingInfoId);
        ShippingInfo editInfo = service.updateAddressInfo(info, userId);
        ShippingInfoResponseDto response = mapper.infoToInfoResponseDto(editInfo);
        var responseDto = ResponseDto.<ShippingInfoResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{shipping-info-id}")
    public ResponseEntity<ResponseDto<ShippingInfoResponseDto>> getShippingInfo(@PathVariable("shipping-info-id") @Positive Long shippingInfoId) {
        ShippingInfo getInfo = service.getAddressInfo(shippingInfoId);
        ShippingInfoResponseDto response = mapper.infoGetResponseDto(getInfo);

        var responseDto = ResponseDto.<ShippingInfoResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<List<ShippingInfoResponseDto>>> getAddressInfoList(@PathVariable@Positive Long userId) {
        List<ShippingInfo> result = service.getInfoList(userId);
        List<ShippingInfoResponseDto> response = mapper.InfoResponseDtoList(result);

        var responseDto = ResponseDto.<List<ShippingInfoResponseDto>>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }


    @DeleteMapping("/{shipping-info-id}")
    public ResponseEntity deleteShippingInfo(@PathVariable("shipping-info-id") @Positive Long shippingInfoId,
                                             @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        service.deleteAddressInfo(shippingInfoId, userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

package project.main.webstore.domain.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.users.dto.ShippingInfoPatchDto;
import project.main.webstore.domain.users.dto.ShippingInfoPostDto;
import project.main.webstore.domain.users.dto.ShippingInfoResponseDto;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.mapper.ShippingMapper;
import project.main.webstore.domain.users.service.ShippingService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.CheckLoginUser;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/address")
@Validated
@RequiredArgsConstructor
public class ShippingController {
    private final ShippingService service;
    private final ShippingMapper mapper;

    @PostMapping
    public ResponseEntity<ResponseDto<ShippingInfoResponseDto>> createInfo(@RequestBody @Valid ShippingInfoPostDto post,
                                     @AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        ShippingInfo info = mapper.infoPostToInfo(post);
        ShippingInfo writeInfo = service.writeInfo(info, userId);
        ShippingInfoResponseDto response = mapper.infoToInfoResponseDto(writeInfo);

        var responseDto = ResponseDto.<ShippingInfoResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{shipping-info-id}")
    public ResponseEntity<ResponseDto<ShippingInfoResponseDto>> patchShippingInfo(@PathVariable("shipping-info-id") @Positive Long shippingInfoId,
                                            @AuthenticationPrincipal Object principal,
                                            @RequestBody @Valid ShippingInfoPatchDto patch) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        ShippingInfo info = mapper.infoPatchToInfo(patch);
        ShippingInfo editInfo = service.editInfo(info, userId);
        ShippingInfoResponseDto response = mapper.infoToInfoResponseDto(editInfo);
        var responseDto = ResponseDto.<ShippingInfoResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{shipping-info-id}")
    public ResponseEntity<ResponseDto<ShippingInfoResponseDto>> getShippingInfo(@PathVariable("shipping-info-id") @Positive Long shippingInfoId) {
        ShippingInfo getInfo = service.getInfo(shippingInfoId);
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
                                             @AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        service.deleteInfo(shippingInfoId, userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

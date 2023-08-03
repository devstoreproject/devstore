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

@RestController
@RequestMapping("/api/shipping")
@Validated
@RequiredArgsConstructor
public class ShippingController {
    private final ShippingService service;
    private final ShippingMapper mapper;

    @PostMapping("/{user-id}/info")
    public ResponseEntity createInfo(@PathVariable("user-id") @Positive Long userId,
                                     @RequestBody @Valid ShippingInfoPostDto postDto,
                                     @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);
        ShippingInfo info = mapper.infoPostToInfo(postDto);
        ShippingInfo writeInfo = service.writeInfo(info, userId);
        ShippingInfoResponseDto response = mapper.infoToInfoResponseDto(writeInfo);

        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{user-id}/{shipping-info-id}")
    public ResponseEntity patchShippingInfo(@PathVariable("user-id") @Positive Long userId,
                                            @PathVariable("shipping-info-id") @Positive Long shippingInfoId,
                                            @AuthenticationPrincipal Object principal,
                                            @RequestBody @Valid ShippingInfoPatchDto patchDto) {
        CheckLoginUser.validUserSame(principal, userId);

        ShippingInfo info = mapper.infoPatchToInfo(patchDto);
        ShippingInfo editInfo = service.editInfo(info, userId);
        ShippingInfoResponseDto response = mapper.infoToInfoResponseDto(editInfo);
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{shipping-info-id}")
    public ResponseEntity getShippingInfo(@PathVariable("shipping-info-id") @Positive Long shippingInfoId) {
        ShippingInfo getInfo = service.getInfo(shippingInfoId);
        ShippingInfoResponseDto response = mapper.infoGetResponseDto(getInfo);

        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }


    @DeleteMapping("/{user-id}/info/{shipping-info-id}")
    public ResponseEntity deleteShippingInfo(@PathVariable("user-id") @Positive Long userId,
                                             @PathVariable("shipping-info-id") @Positive Long shippingInfoId,
                                             @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);
        service.deleteInfo(shippingInfoId, userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

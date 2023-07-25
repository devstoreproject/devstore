package project.main.webstore.domain.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.cart.dto.*;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.mapper.CartMapper;
import project.main.webstore.domain.cart.service.CartService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.UriCreator;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/cart")
@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartMapper mapper;
    @PostMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<CartIdResponseDto>> postCart(@RequestBody CartPostRequestDto post, @PathVariable Long userId){
        List<LocalCartDto> request = mapper.toLocalList(post.getItemList());
        Cart result = cartService.postCart(request, userId);

        CartIdResponseDto response = mapper.toResponseId(result);
        URI location = UriCreator.createUri("/cart/userId", response.getUserId());
        ResponseDto<CartIdResponseDto> responseDto = ResponseDto.<CartIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        return ResponseEntity.created(location).body(responseDto);
    }

    //수량 변경
    @PatchMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<CartIdResponseDto>> patchCart(@PathVariable Long userId, @RequestBody CartPatchRequestDto patch){
        List<LocalCartDto> request = mapper.toLocalList(patch.getItemList());
        Cart result = cartService.patchCart(request, userId);
        CartIdResponseDto response = mapper.toResponseId(result);
        URI location = UriCreator.createUri("/cart/userId", response.getUserId());
        ResponseDto<CartIdResponseDto> responseDto = ResponseDto.<CartIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        return ResponseEntity.ok().location(location).body(responseDto);
    }

    //일부 삭제
    @PatchMapping("/users/{userId}/del")
    public ResponseEntity<ResponseDto<CartIdResponseDto>> patchCart(@PathVariable Long userId,@RequestBody CartDeleteDto delete){

        Cart result = cartService.deleteCartItem(userId, delete.getDeleteIdList());
        CartIdResponseDto response = mapper.toResponseId(result);
        URI location = UriCreator.createUri("/cart/userId", response.getUserId());
        ResponseDto<CartIdResponseDto> responseDto = ResponseDto.<CartIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        return ResponseEntity.ok().location(location).body(responseDto);
    }

    //조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<CartGetResponseDto>> getCart(@PathVariable Long userId){
        Cart result = cartService.getCart(userId);
        CartGetResponseDto response = mapper.toGetResponse(result);
        ResponseDto<CartGetResponseDto> responseDto = ResponseDto.<CartGetResponseDto>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }
}

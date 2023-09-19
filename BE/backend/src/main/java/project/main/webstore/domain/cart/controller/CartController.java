package project.main.webstore.domain.cart.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.main.webstore.domain.cart.dto.CartGetResponseDto;
import project.main.webstore.domain.cart.dto.CartIdResponseDto;
import project.main.webstore.domain.cart.dto.CartPatchRequestDto;
import project.main.webstore.domain.cart.dto.CartPostRequestDto;
import project.main.webstore.domain.cart.dto.LocalCartDto;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.mapper.CartMapper;
import project.main.webstore.domain.cart.service.CartService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.CheckLoginUser;
import project.main.webstore.utils.UriCreator;

@RequestMapping("/api/cart")
@RestController
@Tag(name = "장바구니 API",description = "장바구니 기능")
@RequiredArgsConstructor
public class CartController {
    private static final String DEFAULT_URL = "/cart";
    private final CartService cartService;
    private final CartMapper mapper;

    @PostMapping
    @ApiResponse(responseCode = "201", description = "장바구니 등록")
    public ResponseEntity<ResponseDto<CartIdResponseDto>> postCart(@RequestBody CartPostRequestDto post,
                                                                   @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        List<LocalCartDto> request = mapper.toLocalList(post.getItemList());
        Cart result = cartService.postCart(request, userId);

        CartIdResponseDto response = mapper.toResponseId(result);
        URI location = UriCreator.createUri(DEFAULT_URL + "/userId", response.getUserId());
        ResponseDto<CartIdResponseDto> responseDto = ResponseDto.<CartIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping
    public ResponseEntity<ResponseDto<CartIdResponseDto>> patchCart(@Parameter(hidden = true) @AuthenticationPrincipal Object principal,
                                                                    @RequestBody CartPatchRequestDto patch) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        List<LocalCartDto> request = mapper.toLocalList(patch.getPatchItemList());
        List<Long> deleteOptionRequestId = mapper.toList(patch.getDeleteOptionId());
        Cart result = cartService.patchCart(userId,request,deleteOptionRequestId);
        CartIdResponseDto response = mapper.toResponseId(result);
        URI location = UriCreator.createUri(DEFAULT_URL + "/userId", response.getUserId());
        ResponseDto<CartIdResponseDto> responseDto = ResponseDto.<CartIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        return ResponseEntity.ok().location(location).body(responseDto);
    }


    //조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<CartGetResponseDto>> getCart(@PathVariable Long userId) {
        Cart result = cartService.getCart(userId);
        CartGetResponseDto response = mapper.toGetResponse(result);
        ResponseDto<CartGetResponseDto> responseDto = ResponseDto.<CartGetResponseDto>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }
}

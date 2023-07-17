package project.main.webstore.domain.payment.controller;

import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Prepare;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.payment.service.PaymentService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.CheckLoginUser;
import project.main.webstore.utils.UriCreator;

import java.net.URI;

@RestController
@RequestMapping("api/payment")
@RequiredArgsConstructor
@Tag(name = "주문 API",description = "결제 검증 API")
public class PaymentController {
    private final PaymentService paymentService;

//    백엔드 단에서 결제 시스템 띄울떄 사용
//    @GetMapping("/api/pay")
//    public String payProduct() {
//
//        return "pay-Iamport";
//    }

    @PostMapping("/prepare")
    @ApiResponse(responseCode = "201",description = "결제 정보 사전 등록\n 사전등록된 정보를 추 후 검증할 때 사용하기 위한 저장")
    public ResponseEntity postPrepare(@RequestBody PrepareData prepareData) {
        PrepareData resultPrepare = paymentService.postPrepare(prepareData);
        var responseDto = ResponseDto.<PrepareData>builder().data(resultPrepare).customCode(ResponseCode.CREATED);
        URI location = UriCreator.createUri("api/payment/pre-valid/{orderNumber}", resultPrepare.getMerchant_uid());
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping("/pre-valid/{orderNumber}")
    @ApiResponse(responseCode = "200",description = "결제 정보 사전 검증")
    public ResponseEntity getPrepare(@PathVariable String orderNumber, @RequestParam int price){
        IamportResponse<Prepare> prepare = paymentService.getPrepare(orderNumber, price);
        var responseDto = ResponseDto.<IamportResponse<Prepare>>builder().data(prepare).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/post-vlaid/{orderNumber}")
    @ApiResponse(responseCode = "200",description = "결제 정보 사후 검증")
    public ResponseEntity validPayment(@ModelAttribute PrepareData prepareData,
                                       @RequestParam String iamId,
                                       @PathVariable String orderNumber,
                                       @RequestParam Long userId,
                                       @AuthenticationPrincipal Object principal){  //로그인 성공 시 로직 변경 필요
        CheckLoginUser.validUserSame(principal,userId);
        String s = paymentService.validatePayment(prepareData, iamId, orderNumber, userId);
        var responseDto = ResponseDto.builder().data(s).build();
        return ResponseEntity.ok(responseDto);
    }
    //결제 취소 조회는 order 완성되고 난 이후 작업 진행 예정
}

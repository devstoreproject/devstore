package project.main.webstore.domain.payment.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.main.webstore.domain.payment.dto.PrePareDto;
import project.main.webstore.domain.payment.dto.PrePareResponseDto;
import project.main.webstore.domain.payment.service.PaymentService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.UriCreator;

@RestController
@RequestMapping("api/payment")
@RequiredArgsConstructor
@Tag(name = "주문 API",description = "결제 검증 API")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/prepare")
    @ApiResponse(responseCode = "201",description = "결제 정보 사전 등록\n 사전등록된 정보를 추 후 검증할 때 사용하기 위한 저장")
    public ResponseEntity<ResponseDto<PrePareResponseDto>> postPrepare(@RequestBody PrePareDto post ) {
        var resultPrepare = paymentService.postPrepare(post.getAmount(),post.getOrderNumber());
        PrePareResponseDto prePareResponseDto = new PrePareResponseDto(
                resultPrepare.getMerchant_uid(), resultPrepare.getAmount().longValue());
        var responseDto = ResponseDto.<PrePareResponseDto>builder().data(prePareResponseDto).customCode(ResponseCode.CREATED).build();
        URI location = UriCreator.createUri("api/payment/pre-valid/{orderNumber}", resultPrepare.getMerchant_uid());
        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping("/post-valid/{orderNumber}")
    @ApiResponse(responseCode = "200",description = "결제 정보 사전 검증")
    public ResponseEntity validPayment(@PathVariable String orderNumber){
        Integer amount = paymentService.validatePayment(orderNumber);
        var responseDto = ResponseDto.builder().data(amount).build();
        return ResponseEntity.ok(responseDto);
    }

}

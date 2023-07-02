package project.main.webstore.domain.payment.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Prepare;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.payment.exception.PaymentExceptionCode;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.redis.RedisUtils;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    //    private final OrderService orderService;
    private final IamportClient client;
    private final RedisUtils redisUtils;
    private final int SAVE_PAY_ACCESS_TIME = 10;
    //AOP 이용 가능성 확인   사전 등록 -> 사전 등록 시 레디스에 캐시 메모리로 저장, 이후 진짜 저장할 떄 DB에 저장 하면 좋지 않나? 하는 조그만한 생각을 가지고 있음

    private static void validPriceEquals(int iamPrice, int checkPrice) {
        if (checkPrice != iamPrice) {
            throw new BusinessLogicException(PaymentExceptionCode.PAYMENT_AMOUNT_CONFLICT);
        }
    }

    private static void validPaymentAccessTime(PrepareData findByKey) {
        if(findByKey == null){
            throw new BusinessLogicException(PaymentExceptionCode.PAYMENT_ACCESS_TIME_ERROR);
        }
    }

    //프론트엔드와의 상의를 통해 데이터를 변경할 필요가 있을 수 있음
    public PrepareData postPrepare(String orderNumber, int amount, Orders order) {
        PrepareData prepareData = null;
        try {
            prepareData = new PrepareData(orderNumber, new BigDecimal(amount));
            IamportResponse<Prepare> iamportResponse = client.postPrepare(prepareData);
        } catch (IamportResponseException e) {
            if (e.getHttpStatusCode() == 401) { //검증 실패 ( 토큰 문제)
                log.error("### 결제 서버 권한 문제 발생 {}", e.getMessage());
                log.trace("결제 서버 권한 문제 발생 ", e);
                throw new BusinessLogicException(PaymentExceptionCode.PAYMENT_AUTHENTIC_NOT_FOUND);
            } else {    //500 에러 발생
                log.error("### 결제 서버 에러 발생 {}", e.getMessage());
                log.trace("결제 서버 에러 발생 ", e);
                throw new BusinessLogicException(PaymentExceptionCode.PAYMENT_SERVER_ERROR);
            }
        } catch (IOException e) {
            log.error("### 연결 시 에러 발생");
            log.trace("### 에러 발생", e);
            throw new BusinessLogicException(PaymentExceptionCode.PREPARE_GET_ERROR);
        } catch (AssertionError e) {
            log.error("### 가격 정보 불일치");
            throw new BusinessLogicException(PaymentExceptionCode.PAYMENT_AMOUNT_CONFLICT);
        }
        redisUtils.set(prepareData.getMerchant_uid(),prepareData,SAVE_PAY_ACCESS_TIME);
        return prepareData;
    }

    //사전 저장 데이터와의 일치 여부 체크용
    public String validatePayment(PrepareData prepareData, String impUid, String orderNumber, long userId)
    {
        PrepareData findByKey = (PrepareData) redisUtils.findByKey(prepareData.getMerchant_uid());
        int redisPrice = findByKey.getAmount().intValue();

        validPaymentAccessTime(findByKey);

        int postPrice = prepareData.getAmount().intValue();
        int impPrice = 0;

        try {
            impPrice = client.paymentByImpUid(impUid).getResponse().getAmount().intValue();
        } catch (IamportResponseException e) {
            if (e.getHttpStatusCode() == 401) { //검증 실패 ( 토큰 문제)
                log.error("### 결제 서버 권한 문제 발생 {}", e.getMessage());
                log.trace("결제 서버 권한 문제 발생 ", e);
                throw new BusinessLogicException(PaymentExceptionCode.PAYMENT_AUTHENTIC_NOT_FOUND);
            } else {    //500 에러 발생
                log.error("### 결제 서버 에러 발생 {}", e.getMessage());
                log.trace("결제 서버 에러 발생 ", e);
                throw new BusinessLogicException(PaymentExceptionCode.PAYMENT_SERVER_ERROR);
            }
        } catch (IOException e){
            log.error("### 연결 시 에러 발생");
            log.trace("### 에러 발생", e);
            throw new BusinessLogicException(PaymentExceptionCode.PREPARE_GET_ERROR);
        }
        validPriceEquals(postPrice,impPrice,redisPrice);

        // 일치하면 주문완료로 상태 변경

        // 결제정보 저장

        // 장바구니에 담겨있던 물건 삭제

        return "결제 성공";
    }

    //결제 사후 검증
    private IamportResponse<Prepare> getPrepare(String orderNumber, int price) {
        IamportResponse<Prepare> prepare = null;
        PrepareData findByKey = (PrepareData) redisUtils.findByKey(orderNumber);

        validPaymentAccessTime(findByKey);

        try {
            prepare = client.getPrepare(orderNumber);
            assertEquals(prepare.getCode(), 0);  //0 일치 검증
        } catch (IamportResponseException e) {
            if (e.getHttpStatusCode() == 401) { //검증 실패 ( 토큰 문제)
                log.error("### 결제 서버 권한 문제 발생 {}", e.getMessage());
                log.trace("결제 서버 권한 문제 발생 ", e);
                throw new BusinessLogicException(PaymentExceptionCode.PAYMENT_AUTHENTIC_NOT_FOUND);
            } else {    //500 에러 발생
                log.error("### 결제 서버 에러 발생 {}", e.getMessage());
                log.trace("결제 서버 에러 발생 ", e);
                throw new BusinessLogicException(PaymentExceptionCode.PAYMENT_SERVER_ERROR);
            }
        } catch (IOException e) {
            log.error("### 연결 시 에러 발생");
            log.trace("### 에러 발생", e);
            throw new BusinessLogicException(PaymentExceptionCode.PREPARE_GET_ERROR);

        } catch (AssertionError e) {
            log.error("### 아임포트 연결 에러 발생  = {}", prepare.getMessage());
            log.trace("### 에러 발생", e);
            throw new BusinessLogicException(PaymentExceptionCode.PREPARE_GET_ERROR);
        }

        validPriceEquals(price, findByKey.getAmount().intValue());

        return prepare;
    }

    private void validPriceEquals(int requestPrice, int iamPrice, int redisPrice) {
        if (requestPrice != iamPrice || requestPrice != redisPrice) {

            //환불 로직이 들어가야한다.


            throw new BusinessLogicException(PaymentExceptionCode.FABRICATED_PAYMENT);
        }
    }
}

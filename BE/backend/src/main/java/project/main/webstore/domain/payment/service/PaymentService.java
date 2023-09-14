package project.main.webstore.domain.payment.service;

import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import project.main.webstore.domain.payment.exception.PaymentExceptionCode;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.redis.RedisUtils;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final RedisUtils redisUtils;
    private final RestTemplate template = new RestTemplate();
    private final int SAVE_PAY_ACCESS_TIME = 10;
    private final String IAMPORT_URL = "https://api.iamport.kr";
    @Value("${iamport.merchant_uid}")
    private String merchant_uid;
    @Value("${iamport.api-key}")
    private String api;
    @Value("${iamport.secret-key}")
    private String apiSecret;

    public PrepareData postPrepare(Long amount,String orderNumber) {
        String accessToken = getAccessToken();
        BigDecimal totalPrice = BigDecimal.valueOf(amount);
        PrepareData prepareData = new PrepareData(merchant_uid, totalPrice);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<PrepareData> request = new HttpEntity<>(prepareData, headers);

        ResponseEntity<IamportResponse> response = template.postForEntity(
                IAMPORT_URL + "/payments/prepare", request, IamportResponse.class);
        HttpStatus statusCode = response.getStatusCode();
        if (response.getBody().getResponse() == null) {
            response = template.exchange(IAMPORT_URL + "/payments/prepare", HttpMethod.PUT, request, IamportResponse.class);
        }
        redisUtils.set(orderNumber,amount,SAVE_PAY_ACCESS_TIME);

        return prepareData;
}

    //사전 검증
    public Integer validatePayment(String orderNumber) {
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add("Authorization","Bearer " + accessToken);

        HttpEntity request = new HttpEntity<>( headers);
        ResponseEntity<IamportResponse> response = template.exchange(IAMPORT_URL + "/payments/prepare/{merchant_uid}",HttpMethod.GET,request, IamportResponse.class,"imp82370458");

        LinkedHashMap responseBody = (LinkedHashMap) response.getBody().getResponse();
        Integer amount = (Integer) responseBody.get("amount");
        Integer byKey = (Integer) redisUtils.findByKey(orderNumber);

        if(byKey == null){
            throw new BusinessLogicException(PaymentExceptionCode.PAYMENT_ACCESS_TIME_ERROR);
        }
        if(amount != byKey){
            throw new BusinessLogicException(PaymentExceptionCode.FABRICATED_PAYMENT);
        }
        return amount;
    }

    private String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.put("imp_key", List.of(api));
        params.put("imp_secret", List.of(apiSecret));
        HttpEntity request = new HttpEntity<>(params, headers);

        ResponseEntity<IamportResponse> accessToken = template.exchange(
                IAMPORT_URL + "/users/getToken",
                HttpMethod.POST, request, IamportResponse.class);
        LinkedHashMap response = (LinkedHashMap) accessToken.getBody().getResponse();
        return (String) response.get("access_token");
    }
}

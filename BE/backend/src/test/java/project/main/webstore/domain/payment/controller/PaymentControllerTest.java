package project.main.webstore.domain.payment.controller;

import com.google.gson.Gson;
import com.siot.IamportRestClient.request.PrepareData;
import java.math.BigDecimal;
import java.util.Calendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.main.webstore.domain.payment.dto.PrePareDto;
import project.main.webstore.domain.payment.service.PaymentService;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class PaymentControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    Gson gson;
    @MockBean
    PaymentService service;
    String DEFAULT_URL = "/api/payment";
    @Test
    @DisplayName("결제 정보 사전 검증")
    void prepare_payment_test() throws Exception{
        // given
        String orderNumber = createOrderNumber();
        PrePareDto post = new PrePareDto(1000L, orderNumber);
        String content = gson.toJson(post);
        PrepareData afterService = new PrepareData(orderNumber, BigDecimal.valueOf(1000L));

        BDDMockito.given(service.postPrepare(ArgumentMatchers.anyLong(),ArgumentMatchers.anyString())).willReturn(afterService);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post(DEFAULT_URL + "/prepare").content(content).contentType(
                        MediaType.APPLICATION_JSON));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderNumber").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.amount").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderNumber").value(orderNumber));
    }


    private String createOrderNumber() {
        Calendar cal = Calendar.getInstance();

        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);

        StringBuilder builder = new StringBuilder();
        builder.append(y).append(m).append(d);

        for (int i = 0; i < 10; i++) {
            int random = (int) (Math.random() * 10);
            builder.append(random);
        }

        return builder.toString();
    }
}
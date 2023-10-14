package project.main.webstore.domain.users.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.repository.ShippingRepository;
import project.main.webstore.domain.users.stub.UserStub;

@ExtendWith(MockitoExtension.class)
class ShippingServiceTest {
    @InjectMocks
    ShippingService service;
    @Mock
    ShippingRepository shippingRepository;
    @Mock
    UserValidService userValidService;
    UserStub userStub = new UserStub();

    @Test
    @DisplayName("배송 주소 등록 테스트 : 성공")
    void post_ship_address_test() throws Exception{
        // given
        ShippingInfo request = userStub.createShippingInfo();

        BDDMockito.given(userValidService.validUser(ArgumentMatchers.anyLong())).willReturn(userStub.createUser(1L));
        // when
        ShippingInfo result = service.postAddressInfo(request, 1L);
        // then
        Assertions.assertThat(result.getAddress()).usingRecursiveComparison().isEqualTo(request.getAddress());
    }


}
package project.main.webstore.domain.users.service;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.order.exception.OrderExceptionCode;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
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

    @Test
    @DisplayName("배송 주소 등록 테스트 : 성공")
    void patch_ship_address_test() throws Exception{
        // given
        ShippingInfo request = userStub.createShippingInfo();

        User savedUser = userStub.createUser(1L);
        ShippingInfo savedShipInfo = userStub.createShippingInfo();


        BDDMockito.given(userValidService.validUser(ArgumentMatchers.anyLong())).willReturn(savedUser);
        BDDMockito.given(shippingRepository.findByInfoId(ArgumentMatchers.anyLong())).willReturn(
                Optional.of(savedShipInfo));
        // when
        ShippingInfo result = service.updateAddressInfo(request, 1L);
        // then
        Assertions.assertThat(result.getAddress()).usingRecursiveComparison().isEqualTo(request.getAddress());
        Assertions.assertThat(result.getRecipient()).isEqualTo(request.getRecipient());
    }

    @Test
    @DisplayName("배송 주소 등록 테스트 : 실패")
    void patch_ship_address_fail_test() throws Exception{
        // given
        ShippingInfo request = userStub.createShippingInfo();

        User savedUser = userStub.createUser(1L);
        ShippingInfo savedShipInfo = userStub.createShippingInfo();


        BDDMockito.given(userValidService.validUser(ArgumentMatchers.anyLong())).willReturn(savedUser);
        BDDMockito.given(shippingRepository.findByInfoId(ArgumentMatchers.anyLong())).willReturn(
                Optional.empty());
        // when
        // then
        Assertions.assertThatThrownBy(()-> service.updateAddressInfo(request, 1L)).hasMessage(
                OrderExceptionCode.SHIPPING_INFO_NOT_FOUND.getMessage());
    }



}
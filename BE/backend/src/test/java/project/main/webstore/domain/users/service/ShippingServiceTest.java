package project.main.webstore.domain.users.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
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
    void post_ship_address_test() throws Exception {
        // given
        ShippingInfo request = userStub.createShippingInfo();

        BDDMockito.given(userValidService.validUser(anyLong())).willReturn(userStub.createUser(1L));
        // when
        ShippingInfo result = service.postAddressInfo(request, 1L);
        // then
        Assertions.assertThat(result.getAddress()).usingRecursiveComparison()
                .isEqualTo(request.getAddress());
    }

    @Test
    @DisplayName("배송 주소 등록 테스트 : 성공")
    void patch_ship_address_test() throws Exception {
        // given
        ShippingInfo request = userStub.createShippingInfo();

        User savedUser = userStub.createUser(1L);
        ShippingInfo savedShipInfo = userStub.createShippingInfo();

        BDDMockito.given(userValidService.validUser(anyLong())).willReturn(savedUser);
        BDDMockito.given(shippingRepository.findByInfoId(anyLong())).willReturn(
                Optional.of(savedShipInfo));
        // when
        ShippingInfo result = service.updateAddressInfo(request, 1L);
        // then
        Assertions.assertThat(result.getAddress()).usingRecursiveComparison()
                .isEqualTo(request.getAddress());
        Assertions.assertThat(result.getRecipient()).isEqualTo(request.getRecipient());
    }

    @Test
    @DisplayName("배송 주소 등록 테스트 : 실패")
    void patch_ship_address_fail_test() throws Exception {
        // given
        ShippingInfo request = userStub.createShippingInfo();

        User savedUser = userStub.createUser(1L);
        ShippingInfo savedShipInfo = userStub.createShippingInfo();

        BDDMockito.given(userValidService.validUser(anyLong())).willReturn(savedUser);
        BDDMockito.given(shippingRepository.findByInfoId(anyLong())).willReturn(
                Optional.empty());
        // when
        // then
        Assertions.assertThatThrownBy(() -> service.updateAddressInfo(request, 1L)).hasMessage(
                OrderExceptionCode.SHIPPING_INFO_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("배송지 정보 조회 테스트")
    void get_ship_info_test() throws Exception {
        // given
        ShippingInfo save = userStub.createShippingInfo();
        BDDMockito.given(shippingRepository.findByInfoId(anyLong())).willReturn(
                Optional.of(save));
        // when
        ShippingInfo result = service.getAddressInfo(1L);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(save);
    }

    @Test
    @DisplayName("배송지 정보 조회 테스트 : 실패")
    void get_ship_info_fail_test() throws Exception {
        // given
        BDDMockito.given(shippingRepository.findByInfoId(anyLong())).willReturn(
                Optional.empty());
        // when
        // then
        Assertions.assertThatThrownBy(() -> service.getAddressInfo(1L))
                .hasMessage(OrderExceptionCode.SHIPPING_INFO_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("배송지 정보 전체 조회 테스트 ")
    void get_ship_info_List_test() throws Exception {
        long userId = 1L;
        User savedUser = userStub.createUser(userId);
        savedUser.setShippingInfoList(userStub.createShippingInfoLit(10L));

        // given
        BDDMockito.given(userValidService.validUser(anyLong())).willReturn(savedUser);
        // when
        List<ShippingInfo> result = service.getInfoList(userId);
        // then
        for (int i = 0; i < result.size(); i++) {
            Assertions.assertThat(result.get(i).getUser().getId()).isEqualTo(userId);
        }
    }

    @Test
    @DisplayName("배송지 정보 전체 조회 테스트 ")
    void delete_ship_info_test() throws Exception {
        long userId = 1L;
        User savedUser = userStub.createUser(userId);
        savedUser.setShippingInfoList(userStub.createShippingInfoLit(10L));

        // given
        BDDMockito.given(shippingRepository.findByInfoId(anyLong()))
                .willReturn(Optional.of(userStub.createShippingInfo(1L)));
        BDDMockito.given(userValidService.validUser(anyLong())).willReturn(savedUser);
        BDDMockito.willDoNothing().given(shippingRepository)
                .delete(ArgumentMatchers.any(ShippingInfo.class));
        // when
        service.deleteAddressInfo(1L, 1L);
        // then
        verify(userValidService, times(1)).validUser(1L);
        verify(shippingRepository, times(1)).findByInfoId(1L);
        verify(shippingRepository, times(1)).delete(any(ShippingInfo.class));
    }

}
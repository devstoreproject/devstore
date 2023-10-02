package project.main.webstore.domain.order.stub;

import java.util.List;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.order.dto.OrderPostDto;
import project.main.webstore.domain.order.entity.OrderedItem;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.order.enums.PaymentType;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.helper.TestUtils;
import project.main.webstore.valueObject.Address;

@Component
public class OrderStub extends TestUtils {

    public Orders createOrder(Long id) {
        return new Orders(id, "메시지", 3000, "송장 번호", "우체국",
                OrdersStatus.ORDER_COMPLETE, "주문자",
                new Address("540740", "서울 특별시 동작구", "삼성아파트 302호", "010-1234-1234"), getOrderedItem(),
                new User(1L),
                PaymentType.CARD);
    }

    public OrderPostDto createOrderPost() {
        return OrderPostDto.builder()
                .shippingInfoId(1L)
                .message("메시지")
                .cartItemIdList(List.of(1L))
                .build();
    }

    private List<OrderedItem> getOrderedItem() {
        return List.of(
                new OrderedItem(1L,30000,3000,0,10,"상품 상세1","옵션 상세1",new ItemOption(1L),new Item(1L)),
                new OrderedItem(2L,40000,2000,0,5,"상품 상세2","옵션 상세2",new ItemOption(3L),new Item(2L))
        );

    }
}

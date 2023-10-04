package project.main.webstore.domain.order.stub;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.order.dto.OrderDBDailyPriceDto;
import project.main.webstore.domain.order.dto.OrderDBItemSaleDto;
import project.main.webstore.domain.order.dto.OrderDBMonthlyPriceDto;
import project.main.webstore.domain.order.dto.OrderPostDto;
import project.main.webstore.domain.order.dto.OrderRefundRequestDto;
import project.main.webstore.domain.order.dto.OrderTrackingInfoDto;
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
    public Orders createOrderWithTrackNum(Long id){
        Orders order = createOrder(id);
        order.addDelivery(UUID.randomUUID().toString(),"우체국");
        order.setOrdersStatus(OrdersStatus.DELIVERY_PROGRESS);
        return order;
    }
    public Orders createOrderWithMonth(Long id,int month) {
        Orders order = createOrder(id);
        order.setDateByTest(LocalDateTime.of(2022,month,22,00,30));
        return order;
    }

    public OrderPostDto createOrderPost() {
        return OrderPostDto.builder()
                .shippingInfoId(1L)
                .message("메시지")
                .cartItemIdList(List.of(1L))
                .build();
    }

    public OrderRefundRequestDto createOrderRefundRequestDto() {
        return new OrderRefundRequestDto(List.of(1L));
    }

    public Page<Orders> createOrderPage() {
        ArrayList<Orders> list = new ArrayList<>();
        for (Long i = 1L; i < 10; i++) {
            list.add(createOrder(i));
        }
        return new PageImpl<Orders>(list,super.getPage(),30);
    }
    public Page<Orders> createOrderWithMonthPage(int month) {
        ArrayList<Orders> list = new ArrayList<>();
        for (Long i = 1L; i < 10; i++) {
            list.add(createOrderWithMonth(i,month));
        }
        return new PageImpl<Orders>(list,super.getPage(),30);
    }

    public List<OrderDBMonthlyPriceDto> createMonthlyList(Long totalPrice){
        List<OrderDBMonthlyPriceDto> list = new ArrayList<>();
        for(int i = 1 ; i < 13 ; i++){
            list.add(createMonthlyPrice(i,totalPrice));
        }
        return list;
    }
    public List<OrderDBDailyPriceDto> createDailyList(Long totalPrice) {
        List<OrderDBDailyPriceDto> list = new ArrayList<>();
        for(int i = 1 ; i < 20 ; i++){
            list.add(createDailyPrice(i,totalPrice));
        }
        return list;
    }

    public List<OrderDBItemSaleDto> createItemPriceList(Long totalPrice) {
        List<OrderDBItemSaleDto> list = new ArrayList<>();
        for(Long i = 1L ; i < 20 ; i++){
            list.add(createItemPrice(i,totalPrice));
        }
        return list;
    }

    public OrderDBItemSaleDto createItemPrice(Long itemId,Long itemPrice) {
        return new OrderDBItemSaleDto(itemId,"상품" + itemId,itemPrice,itemPrice);
    }
    public OrderDBMonthlyPriceDto createMonthlyPrice(int month,Long totalPrice){
        return new OrderDBMonthlyPriceDto(2022,month,totalPrice,totalPrice);
    }

    public OrderDBDailyPriceDto createDailyPrice(int day,Long totalPrice){
        return new OrderDBDailyPriceDto(2022,9,day,totalPrice,totalPrice);
    }

    private List<OrderedItem> getOrderedItem() {
        return List.of(
                new OrderedItem(1L,30000,3000,0,10,"상품 상세1","옵션 상세1",new ItemOption(1L,null,new Item(1L),10,null,0),new Item(1L,100000,3000,0)),
                new OrderedItem(2L,40000,2000,0,5,"상품 상세2","옵션 상세2",new ItemOption(3L,null,new Item(2L),5,null,0),new Item(2L,200000,1000,0))
        );

    }

    public OrderTrackingInfoDto createOrderTrackingInfo() {
        return new OrderTrackingInfoDto(UUID.randomUUID().toString(),"우체국");
    }
}

package project.main.webstore.domain.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.order.entity.OrderItem;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
public class OrderItemPostDto {
    @NotNull
    private Long itemId;
    @NotNull
    private int itemCount;

    //TODO: option별 정보..............

}
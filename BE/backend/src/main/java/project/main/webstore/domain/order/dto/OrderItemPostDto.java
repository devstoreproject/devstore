package project.main.webstore.domain.order.dto;

import lombok.Getter;
import lombok.Setter;
import project.main.webstore.domain.item.entity.Item;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OrderItemPostDto {
    @NotNull
    private int itemCount;
    @NotNull
    private String itemName;
    @NotNull
    private int itemPrice;

}

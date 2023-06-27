package project.main.webstore.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponseDto {
    protected int itemCount;
    private String itemName;
    private int itemPrice;
    //    private List<ItemResponseDto> items;
}

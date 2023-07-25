package project.main.webstore.domain.cart.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CartDeleteDto {
    private List<Long> deleteIdList;
}

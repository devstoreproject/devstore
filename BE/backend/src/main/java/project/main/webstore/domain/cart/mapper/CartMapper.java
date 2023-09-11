package project.main.webstore.domain.cart.mapper;

import org.springframework.stereotype.Component;
import project.main.webstore.domain.cart.dto.CartGetResponseDto;
import project.main.webstore.domain.cart.dto.CartIdResponseDto;
import project.main.webstore.domain.cart.dto.CartItemDto;
import project.main.webstore.domain.cart.dto.LocalCartDto;
import project.main.webstore.domain.cart.entity.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public LocalCartDto toLocal(CartItemDto post){
        return new LocalCartDto(post.getOptionId(),post.getItemCount());
    }

    public List<LocalCartDto> toLocalList(List<CartItemDto> itemList){
        return itemList.stream().map(this::toLocal).collect(Collectors.toList());
    }

    public CartIdResponseDto toResponseId(Cart result) {
        return new CartIdResponseDto(result.getUser().getId(),result.getId());
    }

    public CartGetResponseDto toGetResponse(Cart result) {
        if (result != null)
            return new CartGetResponseDto(result);
        return null;
    }
}

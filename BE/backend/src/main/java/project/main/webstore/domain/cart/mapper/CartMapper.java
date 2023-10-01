package project.main.webstore.domain.cart.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.cart.dto.CartGetResponseDto;
import project.main.webstore.domain.cart.dto.CartIdResponseDto;
import project.main.webstore.domain.cart.dto.CartItemDto;
import project.main.webstore.domain.cart.dto.LocalCartDto;
import project.main.webstore.domain.cart.dto.OptionDto;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.image.dto.ImageDto;
import project.main.webstore.domain.image.mapper.ImageMapper;

@Component
public class CartMapper extends ImageMapper {

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
        if(result == null)
            return null;
        return CartGetResponseDto.builder()
                .cartId(result.getId())
                .userId(result.getId())
                .itemList(this.getOptionDtoList(result.getCartItemList()))
                .deliveryPrice(result.getDeliveryPrice())
                .totalPrice(result.getOriginalTotalPrice())
                .discountedPrice(result.getDiscountedTotalPrice())
                .build();
    }
    private List<OptionDto> getOptionDtoList(List<CartItem> cartItemList){
        return cartItemList != null ? cartItemList.stream().map(this::getOptionDto).collect(Collectors.toList()) : new ArrayList<>();
    }

    private OptionDto getOptionDto(CartItem cartItem){
        return OptionDto.builder()
                .itemId(cartItem.getOption().getItem().getItemId())
                .itemName(cartItem.getOption().getItem().getItemName())
                .optionId(cartItem.getOption().getOptionId())
                .count(cartItem.getItemCount())
                .defaultPrice(cartItem.getOption().getItem().getItemPrice())
                .additionalPrice(cartItem.getOption().getAdditionalPrice())
                .discountRate(cartItem.getOption().getItem().getDiscountRate())
                .optionName(cartItem.getOption().getOptionName())
                .optionDetail(cartItem.getOption().getOptionDetail())
                .imageInfo(getImageDto(cartItem))
                .build();

    }

    private ImageDto getImageDto(CartItem cartItem) {
        return cartItem.getOption().getItem().getDefaultImage() != null ? toResponseDto(
                cartItem.getOption().getItem().getDefaultImage()) : null;
    }

    public List<Long> toList(List<Long> idList) {
        return idList == null ? new ArrayList<>() : idList;
    }
}

package project.main.webstore.domain.cart.stub;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.cart.dto.CartItemDto;
import project.main.webstore.domain.cart.dto.CartPostRequestDto;
import project.main.webstore.domain.cart.dto.LocalCartDto;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.stub.ItemStub;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.stub.ImageStub;

@Component
public class CartStub extends ImageStub {
    @Autowired
    private ItemStub itemStub;

    public CartPostRequestDto getCartPostDto() {
        return new CartPostRequestDto(getCartItemDtoList());
    }

    public CartPostRequestDto getCartPostDtoNoDate() {
        return new CartPostRequestDto();
    }

    public Cart getCart(Long id) {
        return Cart.builder()
                .cartItemList(getCartItemList())
                .id(id)
                .user(new User(1L))
                .build();
    }

    public List<CartItem> getCartItemList() {
        return List.of(
            new CartItem(new ItemOption(1L,"상품상세01",new Item(1L),10,"상품 이름01"),new Cart(),10),
            new CartItem(new ItemOption(2L,"상품상세02",new Item(2L),10,"상품 이름02"),new Cart(),20),
            new CartItem(new ItemOption(3L,"상품상세03",new Item(3L),10,"상품 이름03"),new Cart(),30)
        );
    }
    private List<CartItemDto> getCartItemDtoList() {
        return List.of(
                new CartItemDto(1L, 1),
                new CartItemDto(2L, 10),
                new CartItemDto(3L, 20),
                new CartItemDto(4L, 30)
        );
    }

    public List<LocalCartDto> createLocalCartDtoList() {
        return List.of(
                new LocalCartDto(1L,10),
                new LocalCartDto(2L,20),
                new LocalCartDto(3L,30)
        );
    }
}

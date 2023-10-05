package project.main.webstore.domain.cart.stub;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.cart.dto.CartItemDto;
import project.main.webstore.domain.cart.dto.CartPatchRequestDto;
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
    public Cart getCartWithCartItemId(Long id) {
        return Cart.builder()
                .cartItemList(getCartItemListWithId())
                .id(id)
                .user(new User(1L))
                .build();
    }

    public List<CartItem> getCartItemList() {
        return List.of(
            new CartItem(new ItemOption(1L,"상품상세01",new Item(1L,100000,3000,0),10,"상품 이름01",0),new Cart(),10),
            new CartItem(new ItemOption(2L,"상품상세02",new Item(2L,200000,2000,0),10,"상품 이름02",0),new Cart(),20),
            new CartItem(new ItemOption(3L,"상품상세03",new Item(3L,300000,1000,0),10,"상품 이름03",0),new Cart(),30)
        );
    }
    public List<CartItem> getCartItemListWithId() {
        return List.of(
                new CartItem(1L,new ItemOption(1L,"상품상세01",new Item(1L,100000,3000,0),10,"상품 이름01",0),new Cart(),10),
                new CartItem(2L,new ItemOption(2L,"상품상세02",new Item(2L,200000,2000,0),10,"상품 이름02",0),new Cart(),20),
                new CartItem(3L,new ItemOption(3L,"상품상세03",new Item(3L,300000,1000,0),10,"상품 이름03",0),new Cart(),30)
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

    public List<LocalCartDto> createLocalCartPathList() {
        return List.of(
                new LocalCartDto(1L,5)
        );
    }

    //상품 수량 변경만 진행
    public CartPatchRequestDto getCartPatchItemOnlyCountChang() {
        return new CartPatchRequestDto(List.of(new CartItemDto(1L,5)),null);
    }
    public CartPatchRequestDto getCartPatchItemOnlyDelete() {
        return new CartPatchRequestDto(null,List.of(1L));
    }
    public CartPatchRequestDto getCartPatchItem() {
        return new CartPatchRequestDto(List.of(new CartItemDto(1L,5)),List.of(1L));
    }
}
package project.main.webstore.domain.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.cart.dto.LocalCartDto;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.cart.repository.CartRepository;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.service.OptionService;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserValidService userValidService;
    private final OptionService optionService;

    //처음이나 추가 모두 여기 들어가게 된다.
    public Cart postCart(List<LocalCartDto> itemList, Long userId) {
        User findUser = userValidService.validUser(userId);

        List<Long> optionIdList = itemList.stream().map(LocalCartDto::getOptionId).collect(Collectors.toList());

        List<ItemOption> optionList = optionService.getOptions(optionIdList);

        if (findUser.getCart() == null) {
            findUser.setCart(new Cart(findUser));
        }
        Cart cart = findUser.getCart();
        List<CartItem> findCartItem = cart.getCartItemList();

        for(int i = 0 ; i < optionList.size(); i++){
            CartItem cartItem = new CartItem(optionList.get(i), cart, itemList.get(i).getCount());
            findCartItem.add(cartItem);
        }

        return cart;
    }

    public Cart getCart(Long userId) {
        User findUser = userValidService.validUser(userId);
        Cart cart = findUser.getCart();

        return cart;
    }

    public void deleteCart(Long userId) {
        User user = userValidService.validUser(userId);
        Cart cart = user.getCart();
        cartRepository.delete(cart);
    }

    //수량 변경
    public Cart patchCart(List<LocalCartDto> patch, Long userId) {
        User findUser = userValidService.validUser(userId);
        Cart cart = findUser.getCart();

        for (LocalCartDto localCartDto : patch) {
            for (CartItem cartItem : cart.getCartItemList()) {
                if (localCartDto.getOptionId() == cartItem.getOption().getOptionId()) {
                    cartItem.setItemCount(localCartDto.getCount());
                    break;
                }
            }
        }
        return cart;
    }

    //취소 하고 싶은 것들 리스트 -> CartItemList 들로 표현한다.
    public Cart deleteCartItem(Long userId, List<Long> deleteIdList) {
        Cart cart = userValidService.validUser(userId).getCart();
        List<CartItem> cartItemList = cart.getCartItemList();
        List<CartItem> collect = cartItemList.stream().filter(cartItem -> !deleteIdList.contains(cartItem.getOption().getOptionId())).collect(Collectors.toList());
        cart.setCartItemList(collect);
        return cart;
    }

}

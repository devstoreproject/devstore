package project.main.webstore.domain.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.cart.dto.LocalCartDto;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.cart.enums.CartExceptionCode;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.exception.ItemExceptionCode;
import project.main.webstore.domain.item.service.OptionService;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.exception.BusinessLogicException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final UserValidService userValidService;
    private final OptionService optionService;

    //처음이나 추가 모두 여기 들어가게 된다.
    public Cart postCart(List<LocalCartDto> itemList, Long userId) {
        User findUser = userValidService.validUser(userId);

        if (findUser.getCart() == null) {
            Cart cart = new Cart(findUser);
            findUser.setCart(cart);
            cart.setUser(findUser);
        }

        return postCartItem(itemList, findUser.getCart());
    }

    public Cart getCart(Long userId) {
        User findUser = userValidService.validUser(userId);
        return findUser.getCart();
    }

    //수량 변경 / 제거 / 옵션 변경 통합
    public Cart patchCart(Long userId, List<LocalCartDto> patch, List<Long> deleteIdList) {
        Cart cart = validCartExist(userId);
        //데이터 삭제 로직
        List<CartItem> cartItemList = cart.getCartItemList();
        deleteCartItem(cartItemList, deleteIdList);

        List<LocalCartDto> addItemList = new ArrayList<>();
        List<LocalCartDto> patchItemList = new ArrayList<>();

        separatePatchList(patch, cartItemList, addItemList, patchItemList);

        //데이터 추가
        postCartItem(addItemList, cart);
        //데이터 변경
        changeItemCount(patchItemList, cartItemList);

        return cart;
    }

    private void separatePatchList(List<LocalCartDto> patch, List<CartItem> cartItemList,
            List<LocalCartDto> addItemList, List<LocalCartDto> patchItemList) {
        Map<Long, CartItem> checkAddOrPatch = new ConcurrentHashMap<>();

        //저장되어 있는 것들을 넣는다.
        for (CartItem cartItem : cartItemList) {
            checkAddOrPatch.put(cartItem.getOption().getOptionId(), cartItem);
        }

        for (LocalCartDto localCartDto : patch) {
            CartItem put = checkAddOrPatch.put(localCartDto.getOptionId(), new CartItem());
            if (put == null) {
                addItemList.add(localCartDto);
            } else {
                patchItemList.add(localCartDto);
            }
        }
    }

    private void deleteCartItem(List<CartItem> cartItemList, List<Long> deleteIdList) {
        Map<Long, Integer> checkIsDelete = new ConcurrentHashMap<>();
        //삭제할 것들이 먼저 나온다.
        for (Long deleteId : deleteIdList) {
            checkIsDelete.put(deleteId, -1);
        }
        //기존에 있는 상품들이 들어간다.
        for (int i = 0 ; i < cartItemList.size() ; i++) {
            CartItem cartItem = cartItemList.get(i);
            Integer itemCount = checkIsDelete.put(cartItem.getOption().getOptionId(), cartItem.getItemCount());
            //만약 데이터가 null이 아니라면 cartItem을 지워버린다.
            if (itemCount != null) {
                cartItemList.remove(cartItem);
            }
        }

    }

    //수량 변경
    private void changeItemCount(List<LocalCartDto> patch, List<CartItem> savedCartItemList) {
        Map<Long, Integer> checkIsChange = new ConcurrentHashMap<>();

        for (LocalCartDto localCartDto : patch) {
            checkIsChange.put(localCartDto.getOptionId(), localCartDto.getCount());
        }

        for (CartItem cartItem : savedCartItemList) {
            Integer itemCount = checkIsChange.put(cartItem.getOption().getOptionId(),
                    cartItem.getItemCount());
            if (itemCount != null) {
                cartItem.setItemCount(itemCount);
            }
        }

    }

    private void validItemCount(ItemOption itemOption, int orderAmount) {
        if (itemOption.getItemCount() < orderAmount) {
            throw new BusinessLogicException(ItemExceptionCode.ITEM_NOT_ENOUGH);
        }
    }

    private Cart validCartExist(Long userId) {
        User findUser = userValidService.validUser(userId);
        Cart cart = findUser.getCart();
        if (cart == null) {
            throw new BusinessLogicException(CartExceptionCode.Cart_NOT_FOUND);
        }
        return cart;
    }

    private Cart postCartItem(List<LocalCartDto> itemList, Cart cart) {
        List<CartItem> findCartItem = cart.getCartItemList();

        List<ItemOption> optionList = searchOptionList(itemList);

        addItemToCart(itemList, cart, findCartItem, optionList);

        return cart;
    }

    private void addItemToCart(List<LocalCartDto> itemList, Cart cart, List<CartItem> findCartItem,
            List<ItemOption> optionList) {
        for (int i = 0; i < optionList.size(); i++) {
            validItemCount(optionList.get(i), itemList.get(i).getCount());
            CartItem cartItem = new CartItem(optionList.get(i), cart, itemList.get(i).getCount());
            findCartItem.add(cartItem);
        }
    }

    private List<ItemOption> searchOptionList(List<LocalCartDto> itemList) {
        List<Long> optionIdList = itemList.stream().map(LocalCartDto::getOptionId)
                .collect(Collectors.toList());
        return optionService.getOptions(optionIdList);
    }

}

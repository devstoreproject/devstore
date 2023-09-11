package project.main.webstore.domain.users.stub;

import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.users.entity.User;

import java.util.ArrayList;

public class UserStub {
    public User createUser(Long id) {
        return User.stubBuilder()
                .cart(new Cart())
                .userName("김복자")
                .phone("010-1231-1234")
                .email("admin@gmail.com")
                .pickedItemList(new ArrayList<>())
                .nickName("김순자")
                .id(id)
                .password("admin123!!")
                .profileImage("프로필사진 url")
                .build();
    }
}

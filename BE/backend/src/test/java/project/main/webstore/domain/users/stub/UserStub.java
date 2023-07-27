package project.main.webstore.domain.users.stub;

import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.valueObject.Address;

import java.util.ArrayList;

public class UserStub {
    public User createUser(Long id) {
        return User.stubBuilder()
                .cart(new Cart())
                .userName("김복자")
                .mileage(0)
                .address(new Address("540204","서울시 어디동 어디구 어쩌구","314동 502호","010-123-1234"))
                .email("admin@gmail.com")
                .pickedItemList(new ArrayList<>())
                .nickName("김순자")
                .id(id)
                .password("admin123!!")
                .profileImage("프로필사진 url")
                .build();
    }
}

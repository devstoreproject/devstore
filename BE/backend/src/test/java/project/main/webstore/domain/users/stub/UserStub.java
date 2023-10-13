package project.main.webstore.domain.users.stub;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.users.dto.UserGetResponseDto;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.helper.TestUtils;
import project.main.webstore.valueObject.Address;

@Component
public class UserStub extends TestUtils {
    public User createUser(Long id) {
        User user = User.stubBuilder()
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
        user.getCart().setUser(user);
        return user;
    }
    public User createUserAdmin() {
        User user = createUser(10000L);
        user.changeRole(UserRole.ADMIN);
        return user;
    }

    public Page<User> userPage() {
        return new PageImpl(users(),getPage(),30);
    }

    public List<User> users() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            User user = User.stubBuilder()
                .cart(new Cart())
                .userName("김복자")
                .phone("010-1231-1234")
                .email("admin" + i + "@gmail.com")
                .pickedItemList(new ArrayList<>())
                .nickName("김순자" + i)
                .id((long) +i)
                .password("admin123" + i + "!!")
                .profileImage("프로필사진 url")
                .build();
            users.add(user);
        }
        return users;
    }

    public Page<User> getUser() {
        return new PageImpl<User>(users());
    }

    public List<UserGetResponseDto> getUsers() {
        List<UserGetResponseDto> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            UserGetResponseDto user = UserGetResponseDto.builder()
                .userId((long) i)
                .email("amdin" + i + "@gmail.com")
                .password("admin123!!")
                .nickname("김순자" + i)
                .profileImage("프로필 사진 url")
                .phone("010-1234-1234")
                .username("김복자" + i)
                .build();
            users.add(user);
        }
        return users;
    }

    public Page<UserGetResponseDto> getUserPage() {
        return new PageImpl<UserGetResponseDto>(getUsers());
    }

    @NotNull
    private static ShippingInfo createShippingInfo() {
        return new ShippingInfo(1L, "김복자", new Address("123-45", "대한민국", "우리집", "010-1234-6789"),
                null);
    }

    public User createUserWithShippingInfo(Long id) {
        User user = createUser(id);
        ShippingInfo info = createShippingInfo();
        info.setUser(user);
        user.getShippingInfoList().add(info);
        return user;
    }
}

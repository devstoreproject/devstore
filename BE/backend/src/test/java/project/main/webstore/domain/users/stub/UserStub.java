package project.main.webstore.domain.users.stub;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.users.dto.UserGetResponseDto;
import project.main.webstore.domain.users.entity.User;

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
}

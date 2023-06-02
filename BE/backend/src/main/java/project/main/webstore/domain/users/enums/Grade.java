package project.main.webstore.domain.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {
    NORMAL("일반 회원"),VIP("우수 회원"),VVIP("최우수 회원")
    //등급을 추가하고자 한다면 이곳에 추가해 주세요
    ;

    private String description;
}

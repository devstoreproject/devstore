package project.main.webstore.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVE(1, "활동 중"),DORMANT(2, "휴면"),DELETE(3, "삭제")
    //등급을 추가하고자 한다면 이곳에 추가해 주세요
    ;

    private int num;
    private String description;
}

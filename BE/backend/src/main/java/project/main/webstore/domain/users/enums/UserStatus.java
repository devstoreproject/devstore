package project.main.webstore.domain.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    ACTIVE(1,"활동 회원"),
    DELETE(2,"탈퇴 회원"),
    SLEEP(3,"휴면 회원"),
    TMP(4, "임시회원"),

    ;
    @Getter
    private int index;
    @Getter
    private String name;
}

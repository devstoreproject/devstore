package project.main.webstore.domain.item.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

@AllArgsConstructor
@Getter
public enum Category {
    COMPUTER,
    MONITOR,
    MOUSE,
    HEADSET,
    CHAIR,
    DESK
    ;
    public static Category of(String str){
        for (Category value : Category.values()) {
            if(value.name().equals(str)){
                return value;
            }
        }
        throw new BusinessLogicException(CommonExceptionCode.COVERT_ERROR);
    }
}

package project.main.webstore.domain.notice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.main.webstore.domain.notice.exception.NoticeException;
import project.main.webstore.exception.BusinessLogicException;

@Getter
@AllArgsConstructor
public enum NoticeCategory {
    OPERATING("운영"),UPDATE("업데이트"),EVENT("이벤트");

    private final String description;

    public static NoticeCategory of(String str){
        for (NoticeCategory value : NoticeCategory.values()) {
            if(value.name().equals(str)){
                return value;
            }
        }
        throw new BusinessLogicException(NoticeException.NOTICE_CATEGORY_NOT_FOUND);
    }
}

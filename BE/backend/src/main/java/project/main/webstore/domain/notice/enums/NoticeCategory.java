package project.main.webstore.domain.notice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NoticeCategory {
    OPERATING("운영"),UPDATE("업데이트"),EVENT("이벤트");

    private final String description;
}

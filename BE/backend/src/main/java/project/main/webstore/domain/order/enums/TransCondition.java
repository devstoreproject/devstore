package project.main.webstore.domain.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransCondition {
    PLUS(1),MINUS(2);

    private int index;
}

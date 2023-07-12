package project.main.webstore.valueObject;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
public class Price {
    private Integer value;
}

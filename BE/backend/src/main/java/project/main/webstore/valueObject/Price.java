package project.main.webstore.valueObject;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
public class Price {
    private Integer value;

    @Builder
    public Price(Integer value) {
        this.value = value;
    }
}

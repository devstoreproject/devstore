package project.main.webstore.valueObject;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Price {
    private int value;

}

package project.main.webstore.valueObject;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class Duration {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}

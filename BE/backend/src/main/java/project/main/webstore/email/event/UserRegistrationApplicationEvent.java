package project.main.webstore.email.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.email.enums.CheckCondition;

@Getter
public class UserRegistrationApplicationEvent extends ApplicationEvent {
    private User user;
    private CheckCondition condition;

    public UserRegistrationApplicationEvent(Object source, User user,CheckCondition condition) {
        super(source);
        this.user = user;
        this.condition = condition;
    }
}

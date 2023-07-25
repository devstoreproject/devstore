package project.main.webstore.email.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import project.main.webstore.domain.users.entity.User;

@Getter
public class UserRegistrationApplicationEvent extends ApplicationEvent {
    private User user;

    public UserRegistrationApplicationEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}

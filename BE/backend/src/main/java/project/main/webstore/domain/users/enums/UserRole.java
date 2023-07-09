package project.main.webstore.domain.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    CLIENT("ROLE_CLIENT"), ADMIN("ROLE_ADMIN")

    ;
    private String role;
}

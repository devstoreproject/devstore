package project.main.webstore.domain.users.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    CLIENT("ROLE_CLIENT"), ADMIN("ROLE_ADMIN")

    ;
    private String role;

    public static UserRole transEnum(String str){
        if(str.equals(UserRole.CLIENT.name()))
            return UserRole.CLIENT;
        else if (str.equals(UserRole.ADMIN.name())) {
            return UserRole.ADMIN;
        }
        return null;
    }
}

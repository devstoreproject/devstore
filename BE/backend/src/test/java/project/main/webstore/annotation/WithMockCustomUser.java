package project.main.webstore.annotation;

import org.springframework.security.test.context.support.WithSecurityContext;
import project.main.webstore.domain.users.enums.UserRole;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String username() default "복자";
    String grade() default "ADMIN";
    String email() default "admin@gmail.com";
    long userId() default 2L;
    UserRole userRole() default UserRole.ADMIN;
}

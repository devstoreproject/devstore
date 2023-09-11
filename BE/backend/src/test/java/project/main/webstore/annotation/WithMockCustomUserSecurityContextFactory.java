package project.main.webstore.annotation;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import project.main.webstore.security.dto.UserInfoDto;

import java.util.Arrays;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser>
{
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        UserInfoDto userInfoDto = new UserInfoDto(annotation.userId(), annotation.email(), annotation.username(), annotation.userRole());
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(userInfoDto,
                "password",
                Arrays.asList(new SimpleGrantedAuthority(annotation.role())));

        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}

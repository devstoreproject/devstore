package project.main.webstore.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import project.main.webstore.domain.user.entity.User;
import project.main.webstore.domain.user.enums.UserRole;

import java.util.Collection;;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class PrincipalDetails extends User implements UserDetails {
    //UserDetails
    public PrincipalDetails(User user) {
        setId(user.getId());
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        setEmailVerified(user.getEmailVerified());
        setUserRole(user.getUserRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        UserRole roles = this.getUserRole();
        return List.of(new SimpleGrantedAuthority("ROLE_"+ roles.name()));
//        return roles
//                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole))
//                .collect(Collectors.toList());
    }



    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getEmailVerified();

    }
}

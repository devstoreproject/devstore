package project.main.webstore.security.jwt.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.domain.users.enums.UserStatus;
import project.main.webstore.domain.users.exception.UserExceptionCode;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.exception.BusinessLogicException;

import java.util.Collection;
@Component
@RequiredArgsConstructor
public class UserDetailsServiceAuth implements UserDetailsService {
    private final UserValidService userValidService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User findUser = userValidService.validByMail(email);
        return new UserDetail(findUser);
    }

    private final class UserDetail extends User implements UserDetails{

        public UserDetail(User user) {
            super(user.getId(),user.getNickName(),user.getPassword(),user.getEmail(),user.getUserRole(),user.getUserStatus());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return AuthorityUtils.createAuthorityList(this.getUserRole().getRole());
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
            return true;
        }
    }
}

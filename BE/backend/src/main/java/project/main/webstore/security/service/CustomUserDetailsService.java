package project.main.webstore.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.user.entity.User;
import project.main.webstore.domain.user.repository.UserRepository;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;
import project.main.webstore.security.PrincipalDetails;

import java.util.Optional;

@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optional = userRepository.findByEmail(username);
        User findUser = optional.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.USERS_NOT_VALID));

        return new PrincipalDetails(findUser);
    }
}

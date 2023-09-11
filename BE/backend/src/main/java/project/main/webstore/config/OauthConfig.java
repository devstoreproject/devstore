package project.main.webstore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.security.jwt.utils.JwtTokenizer;
import project.main.webstore.security.jwt.utils.TransMessageUtils;
import project.main.webstore.security.oauth2.handler.OAuth2UserSuccessHandler;

@Configuration
@RequiredArgsConstructor
public class OauthConfig {
    private final UserService userService;
    private final JwtTokenizer jwtTokenizer;
    private final TransMessageUtils transMessageUtils;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = clientRegistration();
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    @Bean
    public OAuth2UserSuccessHandler oAuth2UserSuccessHandler(){
        return new OAuth2UserSuccessHandler(jwtTokenizer,userService,clientRegistration(),transMessageUtils);
    }
    private ClientRegistration clientRegistration() {

        return CommonOAuth2Provider
                .GOOGLE
                .getBuilder("google")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

}

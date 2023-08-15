package project.main.webstore.security.oauth2.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.enums.Grade;
import project.main.webstore.domain.users.enums.ProviderId;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;
import project.main.webstore.security.jwt.utils.TransMessageUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final UserService userService;
    private final ClientRegistration clientRegistration;
    private final TransMessageUtils trans;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        String profile = String.valueOf(oAuth2User.getAttributes().get("picture"));
        String name = String.valueOf(oAuth2User.getAttributes().get("name"));
        List<GrantedAuthority> client = AuthorityUtils.createAuthorityList("CLIENT");
        String password = RandomStringUtils.random(20);
        String provider = clientRegistration.getRegistrationId().toUpperCase();
        String providerId = clientRegistration.getClientId();

        User user = new User(name, profile, password, email, 0, null, Grade.NORMAL, ProviderId.of(provider), UserRole.CLIENT);

        userService.oAuth2CreateOrGet(user);
        UserInfoDto info = new UserInfoDto(user);
        String accessToken = jwtTokenizer.delegateRefreshToken(info);
        String refreshToken = jwtTokenizer.delegateRefreshToken(info);

        response.setHeader("Authorization", "Bearer_" + accessToken);
        response.setHeader("Refresh", refreshToken);
        Cookie cookie = trans.createCookie(refreshToken);
        response.addCookie(cookie);

        String url = createURI().toString();
        getRedirectStrategy().sendRedirect(request,response, url);
    }

    private URI createURI(){
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port("8080")
                .path("/api/users/1")
                .build()
                .toUri();
    }

}

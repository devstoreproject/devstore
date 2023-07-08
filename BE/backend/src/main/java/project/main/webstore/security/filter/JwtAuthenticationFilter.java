package project.main.webstore.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.main.webstore.domain.user.dto.LoginDto;
import project.main.webstore.domain.user.entity.User;
import project.main.webstore.security.JwtTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtTokenizer jwtTokenizer;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(JwtTokenizer jwtTokenizer, AuthenticationManager authenticationManager) {
        this.jwtTokenizer = jwtTokenizer;
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){

        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    // Todo: RefreshToken 사용 여부
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
        User user = (User) authResult.getPrincipal();

        String accessToken = delegateAccessToken(user);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        String result = "{\"accessToken\" : \""+accessToken+"\"}";

        response.setStatus(200);
        response.getWriter().write(result);

        this.getSuccessHandler().onAuthenticationSuccess(request,response, authResult);
    }

    private String delegateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("username", user.getEmail());
        claims.put("userRole", user.getUserRole());

        String subject = user.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        return "Bearer_" + jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
    }
}

package project.main.webstore.security.jwt.filter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.redis.RedisUtils;
import project.main.webstore.security.dto.LoginDto;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;
import project.main.webstore.security.jwt.utils.TransMessageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;
    private final RedisUtils redisUtils;
    private final TransMessageUtils transMessageUtils;

    //인증 시도
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        LoginDto loginDto = transMessageUtils.transClass(request);
        log.info("request Body Password = {}", loginDto.getPassword());
        log.info("request Body Username = {}", loginDto.getUsername());

        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }



    //인증 성공 시
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        UserInfoDto userInfo = new UserInfoDto(user);
        String accessToken = jwtTokenizer.delegateAccessToken(userInfo);
        String refreshToken = jwtTokenizer.delegateRefreshToken(userInfo);



        Cookie cookie = transMessageUtils.createCookie(refreshToken);
        response.setHeader("Authorization", "Bearer" + accessToken);
        response.setHeader("Refresh", refreshToken);
        response.addCookie(cookie);
        //레디스 저장 로직 (200분 저장)
        redisUtils.set(refreshToken, userInfo, 420);

        //반환 데이터 타입 생성
        String responseDtoStr = transMessageUtils.getResponseBody(user);

        log.info("#### 응답을 위한 response 의 Member 목록 = {}", responseDtoStr);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(responseDtoStr);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}

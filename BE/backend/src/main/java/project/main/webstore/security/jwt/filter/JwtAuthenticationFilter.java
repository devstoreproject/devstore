package project.main.webstore.security.jwt.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.redis.RedisUtils;
import project.main.webstore.security.dto.LoginDto;
import project.main.webstore.security.dto.LoginResponseDto;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenizer jwtTokenizer;
    private final ObjectMapper objectMapper;
    private final RedisUtils redisUtils;

    //인증 시도
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("request Body = {}", request.getInputStream().toString());
        LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);

        var authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    //인증 성공 시
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        UserInfoDto userInfo = new UserInfoDto(user);
        String accessToken = delegateAccessToken(userInfo);
        String refreshToken = delegateRefreshToken(userInfo);

        response.setHeader("Authorization", "Bearer" + accessToken);
        response.setHeader("Refresh", refreshToken);

        //레디스 저장 로직 (200분 저장)
        redisUtils.set(refreshToken, userInfo, 200);

        //반환 데이터 타입 생성
        String responseDtoStr = getResponseBody(user);

        log.info("#### 응답을 위한 response 의 Member 목록 = {}", responseDtoStr);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(responseDtoStr);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    private String getResponseBody(User user) throws JsonProcessingException {
        LoginResponseDto loginResponseDto = new LoginResponseDto(user);
        var responseDto = ResponseDto.builder().data(loginResponseDto).customCode(ResponseCode.OK).build();
        String responseDtoStr = objectMapper.writeValueAsString(responseDto);
        return responseDtoStr;
    }

    private String delegateAccessToken(UserInfoDto userInfo) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("userInfo", userInfo);
        String subject = userInfo.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    // (6)
    private String delegateRefreshToken(UserInfoDto userInfo) {
        String subject = userInfo.getEmail();
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }
}

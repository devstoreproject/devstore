package project.main.webstore.security;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.exception.ErrorResponse;
import project.main.webstore.redis.RedisUtils;
import project.main.webstore.security.dto.LoginDto;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;
import project.main.webstore.security.jwt.utils.TransMessageUtils;

@RequestMapping("/api")
@RestController
@AllArgsConstructor
@Tag(name = "로그인 API", description = "로그인, 로그아웃, 토큰 재발급용")
public class AuthController {
    private final RedisUtils redisUtils;
    private final JwtTokenizer tokenizer;
    private final TransMessageUtils transUtils;
    private final UserService userService;
    @PostMapping("/auth/refresh")
    @ApiResponse(responseCode = "200",description = "엑세스 시간 만료 시 사용되는 API\n Cookie 에 있는 리프레쉬 토큰을 통해 재발급")
    public ResponseEntity getAccessToken(@CookieValue(value = "refreshToken",required = false) String refresh,@RequestHeader(value = "Refresh",required = false)String refreshHeader, HttpServletResponse response) {
        String token = refreshHeader != null ? refreshHeader : refresh;
        UserInfoDto userInfo = (UserInfoDto) redisUtils.findByKey(token);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Location", "/api/login").body(ErrorResponse.of(HttpStatus.UNAUTHORIZED));
        }

        String accessToken = tokenizer.delegateAccessToken(userInfo);
        String refreshToken = tokenizer.delegateRefreshToken(userInfo);

        redisUtils.delete(token);
        redisUtils.set(refreshToken, userInfo, 420);

        Cookie cookie = transUtils.createCookie(refreshToken);
        response.addCookie(cookie);
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization","Bearer"+accessToken);
        header.add("Refresh",refreshToken);
        if(refresh != null)     //토큰 쿠키로 받을 수 있을 때
            return ResponseEntity.noContent().header("Authorization", accessToken).build();
        return ResponseEntity.noContent().headers(header).build();
    }

    @PostMapping("/log-out")
    @ApiResponse(responseCode = "204",description = "로그 아웃 시 서버에 저장된 리프레쉬 토큰 정보 삭제")
    public ResponseEntity logout(@CookieValue(value = "refreshToken",required = false) String refresh,@RequestHeader(value = "Refresh",required = false)String token) {
        if (refresh != null && redisUtils.findByKey(refresh) != null) {
            redisUtils.delete(refresh);
        }else if(token != null && redisUtils.findByKey(token) != null){
            redisUtils.delete(token);
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/active")
    //휴면계정 해제 시 로직 어떻게 만들어지는지 확인할 필요성 존재
    @ApiResponse(responseCode = "204",description = "성공 시 204와 함께 email 전송")
    public ResponseEntity transActiveUserStatus(@RequestBody LoginDto login){
        userService.transActive(login.getUsername(),login.getPassword());

        return ResponseEntity.noContent().build();
    }

}

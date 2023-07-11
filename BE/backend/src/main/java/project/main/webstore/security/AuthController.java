package project.main.webstore.security;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.exception.ErrorResponse;
import project.main.webstore.redis.RedisUtils;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;
import project.main.webstore.security.jwt.utils.TransMessageUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api/auth")
@RestController
@AllArgsConstructor
public class AuthController {
    private final RedisUtils redisUtils;
    private final JwtTokenizer tokenizer;
    private final TransMessageUtils transUtils;

    @GetMapping("/refresh")
    public ResponseEntity getAccessToken(@CookieValue("refreshToken") String refresh, HttpServletResponse response) {
        if (refresh == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Location", "/api/login").body(ErrorResponse.of(HttpStatus.UNAUTHORIZED));
        }
        UserInfoDto userInfo = (UserInfoDto) redisUtils.findByKey(refresh);
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Location", "/api/login").body(ErrorResponse.of(HttpStatus.UNAUTHORIZED));
        }

        redisUtils.delete(refresh);
        redisUtils.set(refresh, userInfo, 420);
        String accessToken = tokenizer.delegateAccessToken(userInfo);
        String refreshToken = tokenizer.delegateRefreshToken(userInfo);
        Cookie cookie = transUtils.createCookie(refreshToken);
        response.addCookie(cookie);


        return ResponseEntity.noContent().header("Authorization", accessToken).build();

    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(@CookieValue("refreshToken") String refresh) {
        Object byKey = redisUtils.findByKey(refresh);
        if (byKey != null) {
            redisUtils.delete(refresh);
        }

        return ResponseEntity.noContent().build();
    }
}

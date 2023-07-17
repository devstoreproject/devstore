package project.main.webstore.security;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RequestMapping("/api")
@RestController
@AllArgsConstructor
@Tag(name = "로그인 관련 API", description = "로그아웃, 토큰 재발급용")
public class AuthController {
    private final RedisUtils redisUtils;
    private final JwtTokenizer tokenizer;
    private final TransMessageUtils transUtils;

    @GetMapping("/auth/refresh")
    @ApiResponse(responseCode = "200",description = "엑세스 시간 만료 시 사용되는 API\n Cookie 에 있는 리프레쉬 토큰을 통해 재발급")
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

    @DeleteMapping("/log-out")
    @ApiResponse(responseCode = "204",description = "로그 아웃 시 서버에 저장된 리프레쉬 토큰 정보 삭제")
    public ResponseEntity logout(@CookieValue("refreshToken") String refresh) {
        Object byKey = redisUtils.findByKey(refresh);
        if (byKey != null) {
            redisUtils.delete(refresh);
        }

        return ResponseEntity.noContent().build();
    }
}

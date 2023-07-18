package project.main.webstore.security;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.exception.ErrorResponse;
import project.main.webstore.redis.RedisUtils;
import project.main.webstore.security.dto.LoginDto;
import project.main.webstore.security.dto.LoginResponseDto;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;
import project.main.webstore.security.jwt.utils.TransMessageUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/api")
@RestController
@AllArgsConstructor
@Tag(name = "로그인 관련 API", description = "로그아웃, 토큰 재발급용")
public class AuthController {
    private final RedisUtils redisUtils;
    private final JwtTokenizer tokenizer;
    private final TransMessageUtils transUtils;
    private final UserService userService;
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

    @GetMapping(path = "/tmp/login")
    @ApiResponse(responseCode = "200",description = "이 것은 임시 다른것과 모든것이 동일하지만 URL이 /api/login 으로 입력하셔야합니다.")
    public ResponseEntity<ResponseDto<LoginResponseDto>> tmpLogin(@Parameter(example = "admin001@gmail.com") @RequestParam String username,@Parameter(example = "admin111")@RequestParam String password, HttpServletResponse response) throws IOException {
        LoginDto login = new LoginDto(username, password);
        User user = userService.tmpLogin(login);
        LoginResponseDto loginResponseDto = new LoginResponseDto(user);
        UserInfoDto userInfo = new UserInfoDto(user);
        String accessToken = tokenizer.delegateAccessToken(userInfo);
        String refreshToken = tokenizer.delegateRefreshToken(userInfo);
        Cookie cookie = transUtils.createCookie(refreshToken);
        response.setHeader("Authorization", "Bearer" + accessToken);
        response.setHeader("Refresh", refreshToken);
        response.addCookie(cookie);

        ResponseDto<LoginResponseDto> build = ResponseDto.<LoginResponseDto>builder().data(loginResponseDto).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(build);

    }
}

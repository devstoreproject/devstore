package project.main.webstore.security.jwt.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.security.dto.LoginDto;
import project.main.webstore.security.dto.LoginResponseDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TransMessageUtils {
    private final ObjectMapper objectMapper;

    public String getResponseBody(User user) throws JsonProcessingException {
        LoginResponseDto loginResponseDto = new LoginResponseDto(user);
        var responseDto = ResponseDto.builder().data(loginResponseDto).customCode(ResponseCode.OK).build();
        String responseDtoStr = objectMapper.writeValueAsString(responseDto);
        return responseDtoStr;
    }

    public Cookie createCookie(String refreshToken){
        String cookieName = "refreshToken";
        Cookie cookie = new Cookie(cookieName, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/api/auth");
        cookie.setMaxAge(60 * 200);
        return cookie;
    }
    public LoginDto transClass(HttpServletRequest request) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //파라미터 방식
        if(username != null && password != null)
            return new LoginDto(username,password);

        //바디 방식
        return objectMapper.readValue(request.getInputStream(), LoginDto.class);
    }
}

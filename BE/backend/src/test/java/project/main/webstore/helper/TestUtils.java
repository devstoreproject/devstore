package project.main.webstore.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;

import java.util.List;

@Component
public class TestUtils {
    @Autowired
    JwtTokenizer jwtTokenizer;
    public HttpHeaders getJWTAdmin(){
        UserInfoDto userInfo = new UserInfoDto("2", "admin@gmail.com", "김복자", UserRole.ADMIN);
        String accessToken = jwtTokenizer.delegateAccessToken(userInfo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }
    public HttpHeaders getJWTClient(){
        UserInfoDto userInfo = new UserInfoDto("1", "client@gmail.com", "김복자", UserRole.CLIENT);
        String accessToken = jwtTokenizer.delegateAccessToken(userInfo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }
    public HttpHeaders getDefaultHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }

}


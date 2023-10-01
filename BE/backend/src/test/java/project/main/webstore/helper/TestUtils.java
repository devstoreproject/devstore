package project.main.webstore.helper;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;

@Component
public class TestUtils {
    @Autowired
    private JwtTokenizer jwtTokenizer;
    public String getJWTAccessTokenAdmin(){
        UserInfoDto userInfo = new UserInfoDto("2", "admin@gmail.com", "김복자", UserRole.ADMIN);
        return jwtTokenizer.delegateAccessToken(userInfo);

    }
    public String getJWTAccessTokenClient(){
        UserInfoDto userInfo = new UserInfoDto("1", "client@gmail.com", "김복자", UserRole.CLIENT);
        return jwtTokenizer.delegateAccessToken(userInfo);

    }
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
    public HttpHeaders getLoginHeader(String accessToken){
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
    public HttpHeaders getMultipartHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }
    public HttpEntity<String> getJsonRequestHeader(String jsonData){

        HttpHeaders jsonRequest = new HttpHeaders();
        jsonRequest.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<String>(jsonData, jsonRequest);
    }

    public MultiValueMap getPageParam(){
        MultiValueMap<String,String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page","0");
        queryParams.add("size","30");
        return queryParams;
    }

    public Pageable getPage(){
        return PageRequest.of(0,30);
    }
}


package project.main.webstore.security.jwt.entryPoint;

import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import project.main.webstore.exception.ErrorResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class UserAuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Exception exception = (Exception) request.getAttribute("exception");
        if(exception instanceof SignatureException){
            sendError(response,HttpStatus.UNAUTHORIZED,false);
        }else if(exception instanceof ExpiredJwtException){
            sendError(response,HttpStatus.UNAUTHORIZED,true);
        }else{
            sendError(response,HttpStatus.FORBIDDEN,false);
        }

        logExceptionMessage(authException, exception);

    }
    private void logExceptionMessage(AuthenticationException authException, Exception exception) {
        String message = exception != null ? exception.getMessage() : authException.getMessage();
        log.warn("Unauthorized error happened: {}", message);
        log.error("authException", authException);
        log.error("Exception ", exception);
    }
    private void sendError(HttpServletResponse response, HttpStatus status,boolean flag) throws IOException {
        Gson gson = new Gson();
        ErrorResponse errorResponse = ErrorResponse.of(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(status.value());
        if(flag){
            response.setHeader("Location","refreshURL");
        }
        response.getWriter().write(gson.toJson(errorResponse, ErrorResponse.class));
    }
}

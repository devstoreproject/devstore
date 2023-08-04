package project.main.webstore.domain.log.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

@Slf4j
public class LogUrlMethodFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("log filter doFilter");

        HttpServletRequest httpResult = (HttpServletRequest) request;
        String requestURI = httpResult.getRequestURI();
        String requestMethod = httpResult.getMethod();

        String uuid = UUID.randomUUID().toString();
        LocalTime startTime = LocalTime.now();

        try {
            log.info("REQUEST [{}][{}] : 실행 시간  = {}", requestMethod, requestURI, startTime);
            log.info("REQUEST Authorization : {}", httpResult.getHeader("Authorization"));
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            Duration duration = Duration.between(startTime, LocalTime.now());
            log.info("RESPONSE [{}][{}] : 실행 시간  = {}", requestMethod, requestURI, LocalTime.now());
            log.info("       요청 응답 시간 : {}", duration.getSeconds());
        }
    }
}

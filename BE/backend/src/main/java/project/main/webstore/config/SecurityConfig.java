package project.main.webstore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.main.webstore.redis.RedisUtils;
import project.main.webstore.security.jwt.entryPoint.UserAuthEntryPoint;
import project.main.webstore.security.jwt.filter.JwtAuthenticationFilter;
import project.main.webstore.security.jwt.filter.JwtVerificationFilter;
import project.main.webstore.security.jwt.handler.FailAuthenticationHandler;
import project.main.webstore.security.jwt.handler.SuccessAuthenticationHandler;
import project.main.webstore.security.jwt.utils.JwtTokenizer;
import project.main.webstore.security.jwt.utils.TransMessageUtils;
import project.main.webstore.security.oauth2.handler.OAuth2UserSuccessHandler;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    private final RedisUtils redisUtils;
    private final TransMessageUtils transMessageUtils;
    private final OAuth2UserSuccessHandler oAuth2UserSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .headers().frameOptions().sameOrigin(); //동일 출처 request만 페이지 렌더링
        http
                .csrf().disable()        // CSRF 공격 시큐리티 비활성화
                .cors(withDefaults())    // corsConfigurationSource 이름의 등록 빈 사용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .formLogin().disable()   // 폼로그인 비활성화
                .httpBasic().disable()   //전송마다 사용자 이름과 비밀번호 헤더에 같이 전달
                .apply(new CustomFilterConfigurer());
        http
                .oauth2Login(oauth2 -> oauth2.loginPage("/api/oauth2/authorization/custom").successHandler(oAuth2UserSuccessHandler).authorizationEndpoint(endpoint -> endpoint.baseUri("/api/oauth2/authorization")));

        http
                .exceptionHandling()
                .authenticationEntryPoint(new UserAuthEntryPoint());
        http
                .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()                // 요청에 대한 권한별 분류
                );
        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE","OPTIONAL","OPTION"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Location", "Refresh","authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }



    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer,redisUtils,transMessageUtils);
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");          // 로그인 경로 체크
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new SuccessAuthenticationHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new FailAuthenticationHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer);

            builder.addFilter(jwtAuthenticationFilter)  //필터체인에 추가
                    .addFilterAfter(jwtVerificationFilter,JwtAuthenticationFilter.class);
        }
    }


}

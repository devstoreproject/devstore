package project.main.webstore.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.main.webstore.redis.RedisUtils;
import project.main.webstore.security.jwt.entryPoint.UserAuthEntryPoint;
import project.main.webstore.security.jwt.filter.JwtAuthenticationFilter;
import project.main.webstore.security.jwt.filter.JwtVerificationFilter;
import project.main.webstore.security.jwt.handler.SuccessAuthenticationHandler;
import project.main.webstore.security.jwt.utils.JwtTokenizer;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenizer jwtTokenizer;
    private final ObjectMapper objectMapper;
    private final RedisUtils redisUtils;
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
                .exceptionHandling()
                .authenticationEntryPoint(new UserAuthEntryPoint());
        http
                .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()                // 요청에 대한 권한별 분류
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));   // 스크립트 기반 Http 통신 허용 설정
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();   // 구현체
        source.registerCorsConfiguration("/**", configuration);     //모든 URL에 정책 적용
        return source;
    }



    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer,objectMapper,redisUtils);
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");          // 로그인 경로 체크
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new SuccessAuthenticationHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer);

            builder.addFilter(jwtAuthenticationFilter)  //필터체인에 추가
                    .addFilterAfter(jwtVerificationFilter,JwtAuthenticationFilter.class);
        }
    }
}

package project.main.webstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .headers().frameOptions().sameOrigin(); //동일 출처 request만 페이지 렌더링
        http
                .csrf().disable()        // CSRF 공격 시큐리티 비활성화
                .cors(withDefaults())    // corsConfigurationSource 이름의 등록 빈 사용
                .formLogin().disable()   // 폼로그인 비활성화
                .httpBasic().disable()   //전송마다 사용자 이름과 비밀번호 헤더에 같이 전달
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()                // 요청에 대한 권한별 분류
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
}

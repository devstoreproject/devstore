package project.main.webstore.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.main.webstore.security.JwtTokenizer;
import project.main.webstore.security.filter.JwtAuthenticationFilter;
import project.main.webstore.security.filter.JwtVerificationFilter;
import project.main.webstore.security.handler.UserAuthenticationFailureHandler;
import project.main.webstore.security.handler.UserAuthenticationSuccessHandler;
import project.main.webstore.security.service.CustomUserDetailsService;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, CustomUserDetailsService userDetailsService) {
        this.jwtTokenizer = jwtTokenizer;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(new CustomFilterConfigurer1())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() //추후 수정
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); //모든 출처에대해 스크립트 기반의 HTTP 통신 허용;
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("POST","GET","PATCH", "DELETE","OPTION"));

        UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;

    }

    /*
    SpringSecurityFilter 등록
    JwtAuthenticationFilter, JwtVerificationFilter
     */
    public class CustomFilterConfigurer1 extends AbstractHttpConfigurer<CustomFilterConfigurer1, HttpSecurity> {

        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenizer, authenticationManager);
            jwtAuthenticationFilter.setFilterProcessesUrl("/users/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, userDetailsService);

            builder.addFilter(jwtAuthenticationFilter);
            builder.addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
            builder.addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }
}

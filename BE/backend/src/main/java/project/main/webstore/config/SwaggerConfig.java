package project.main.webstore.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import project.main.webstore.security.jwt.filter.JwtAuthenticationFilter;

import java.util.Map;
import java.util.Optional;

//@OpenAPIDefinition(
//        info = @Info(
//                title = "devstore API 명세",
//                description = "API 명세서",
//                version = "v1",
//                contact = @Contact(
//                        name = "devstore BE developer",
//                        email = "siglee2247@gmail.com"
//                )
//        )
//)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    private final ApplicationContext applicationContext;

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme bearAuth = new SecurityScheme().name("Bearer Authentication").type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("Bearer");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearAuth");
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearAuth", bearAuth))
                .addSecurityItem(securityRequirement)
                .info(apiInfo())
                ;
    }

    private Info apiInfo() {
        return new Info()
                .title("Springdoc 테스트")
                .description("DEV-STORE BackEnd-API")
                .version("1.0.0")
                .contact(new Contact().email("siglee2247@gmail.com")
                        .name("dev-store BE developer")
                        .extensions(Map.of("email", "boj480@gmail.com")))
                .summary("쇼핑몰 개발을 위한 API")
                .termsOfService("BackEndOfDevStore")
                ;
    }
    @Bean
    public OpenApiCustomiser authLoginOpenApi() {
        FilterChainProxy filterChainProxy = applicationContext.getBean(
                AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, FilterChainProxy.class);
        return openAPI -> {
            for (SecurityFilterChain filterChain : filterChainProxy.getFilterChains()) {
                Optional<JwtAuthenticationFilter> optionalFilter =
                        filterChain.getFilters().stream()
                                .filter(JwtAuthenticationFilter.class::isInstance)
                                .map(JwtAuthenticationFilter.class::cast)
                                .findAny();
                if (optionalFilter.isPresent()) {
                    JwtAuthenticationFilter jwtAuthenticationFilter = optionalFilter.get();
                    Operation operation = new Operation();
                    Schema<?> schema = new ObjectSchema()
                            .addProperties(jwtAuthenticationFilter.getUsernameParameter(), new StringSchema())
                            .addProperties(jwtAuthenticationFilter.getPasswordParameter(),
                                    new StringSchema());
                    RequestBody requestBody = new RequestBody().content(
                            new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                                    new MediaType().schema(schema)));
                    operation.requestBody(requestBody);
                    ApiResponses apiResponses = new ApiResponses();
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()),
                            new ApiResponse().description(HttpStatus.OK.getReasonPhrase()));
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            new ApiResponse().description(HttpStatus.BAD_REQUEST.getReasonPhrase()));
                    operation.responses(apiResponses);
                    operation.addTagsItem("회원 로그인 & 로그아웃");
                    PathItem pathItem = new PathItem().post(operation);
                    openAPI.getPaths().addPathItem("/api/login", pathItem);
                }
            }
        };
    }

    @Bean
    public GroupedOpenApi totalApi() {
        return commonApi()
                .pathsToMatch("/api/**")
                .group("F1")
                .displayName("TOTAL")
                .build();
    }

    @Bean
    public GroupedOpenApi frontOneApi(){
        return commonApi()
                .group("F1")
                .displayName("front001")
                .pathsToMatch("/api/users/**","api/auth/**","api/items/*/favorite")
                .build();
    }
    //매출 내역 판매현황 문의 내역 주문 내역 운송장번호 입력 누락
    //주문 목록 누락
    //

    @Bean
    public GroupedOpenApi frontTwoApi(){
        return commonApi()
                .group("F2")
                .displayName("front002")
                .pathsToMatch()
                .build();
    }

    @Bean
    public GroupedOpenApi frontThreeApi(){
        return commonApi()
                .group("F3")
                .displayName("front003")
                .pathsToMatch()
                .build();
    }

    @Bean
    public GroupedOpenApi frontForeApi(){
        return commonApi()
                .group("F4")
                .displayName("front004")
                .pathsToMatch()
                .build();
    }


    private GroupedOpenApi.Builder commonApi(){
        return GroupedOpenApi.builder()
                .addOpenApiCustomiser(authLoginOpenApi());
    }
}


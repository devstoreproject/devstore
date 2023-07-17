package project.main.webstore.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

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
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                ;
    }

    private Info apiInfo() {
        return new Info()
                .title("Springdoc 테스트")
                .description("Springdoc을 사용한 Swagger UI 테스트")
                .version("1.0.0")
                .contact(new Contact().email("siglee2247@gmail.com")
                        .name("dev-store BE developer")
                        .extensions(Map.of("email","boj480@gmail.com")))
                .summary("쇼핑몰 개발을 위한 API")
                .termsOfService("BackEndOfDevStore");
    }
}


package project.main.webstore.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "devstore API 명세",
                description = "API 명세서",
                version = "v1",
                contact = @Contact(
                        name = "devstore BE developer",
                        email = "siglee2247@gmail.com"
                )
        )
)
@Configuration
public class SwaggerConfig {

}


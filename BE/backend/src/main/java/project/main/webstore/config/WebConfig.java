package project.main.webstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.main.webstore.converter.EnumConverter;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new EnumConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter octetStreamJsonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "octet-stream")));
        return converter;
    }
}

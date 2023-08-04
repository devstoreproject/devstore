package project.main.webstore.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.main.webstore.converter.EnumConverter;
import project.main.webstore.log.filter.LogUrlMethodFilter;

import javax.servlet.Filter;
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
        converter.setSupportedMediaTypes(Arrays.asList(new MediaType("application","json"),new MediaType("application", "octet-stream")));
        return converter;
    }
    @Bean
    public FilterRegistrationBean logFilter() {
        //스프링 부트가 was를 들고 띄우기 때문에 was 띄울 때 필터를 같이 띄워준다.

        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogUrlMethodFilter());
        filterFilterRegistrationBean.setOrder(1);   //우선순위 설정
        filterFilterRegistrationBean.addUrlPatterns("/*");  //필터 적용 url 설정
        return filterFilterRegistrationBean;
    }
}

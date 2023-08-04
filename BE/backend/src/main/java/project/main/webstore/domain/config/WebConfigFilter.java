package project.main.webstore.domain.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import project.main.webstore.domain.log.filter.LogUrlMethodFilter;

import javax.servlet.Filter;

@Configuration
public class WebConfigFilter {

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


package project.main.webstore.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class javaConfig {
    @Value("${iamport.api-key}")
    private String apiKey;
    @Value("${iamport.secret-key}")
    private String secretKey;

    @Bean
    public IamportClient iamportClient(){
        return new IamportClient(apiKey, secretKey);
    }

}

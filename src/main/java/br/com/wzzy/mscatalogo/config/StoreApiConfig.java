package br.com.wzzy.mscatalogo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class StoreApiConfig {

    @Value("${fakestore.api.url}")
    private String baseUrl;

    @Bean
    public WebClient fakeStoreWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}

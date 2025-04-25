package site.brainbrain.iqtest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private static final String TOSS_PAYMENTS_URL = "https://api.tosspayments.com";

    @Bean
    public RestClient tossRestClient() {
        return RestClient.builder()
                .baseUrl(TOSS_PAYMENTS_URL)
                .build();
    }
}

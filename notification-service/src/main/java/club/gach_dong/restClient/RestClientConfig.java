package club.gach_dong.restClient;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@AllArgsConstructor
public class RestClientConfig {
    private final HeaderInterceptor headerInterceptor;

    @Bean
    RestClient restClient() {
        return RestClient.builder()
                .requestInterceptor(headerInterceptor)
                .build();
    }
}

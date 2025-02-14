package club.gach_dong.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${app.gateway.endpoint}")
    private String gatewayEndpoint;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .addServersItem(serverItem())
                .components(component());

    }

    private Components component() {
        return new Components()
                .addSecuritySchemes("Authorization",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .bearerFormat("JWT"));
    }

    private Info apiInfo() {
        return new Info()
                .title("가츠동 API 명세 - 알림 서비스")
                .description("알림 서비스에 대한 API 명세입니다.")
                .version("v1");
    }

    private Server serverItem() {
        return new Server()
                .url(gatewayEndpoint + "/notification/")
                .description("알림 서비스 URL");
    }
}
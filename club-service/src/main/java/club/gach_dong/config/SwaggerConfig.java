package club.gach_dong.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
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
                .components(component())
                .components(defaultJsonResponse());
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
                .title("가츠동 API 명세 - 동아리 서비스")
                .description("동아리 서비스에 대한 API 명세입니다.")
                .version("v1");
    }

    private Server serverItem() {
        return new Server()
                .url(gatewayEndpoint + "/club/")
                .description("동아리 서비스 URL");
    }

    private Components defaultJsonResponse() {
        ApiResponse jsonResponse = new ApiResponse()
                .description("Default JSON response")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()));

        return new Components().addResponses("defaultJsonResponse", jsonResponse);
    }
}
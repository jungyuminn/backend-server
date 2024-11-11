package club.gach_dong.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final UserReferenceIdParameterResolver userReferenceIdParameterResolver;

    public WebConfig(UserReferenceIdParameterResolver userReferenceIdParameterResolver) {
        this.userReferenceIdParameterResolver = userReferenceIdParameterResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userReferenceIdParameterResolver);
    }
}
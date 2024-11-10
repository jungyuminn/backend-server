package club.gach_dong.config;

import club.gach_dong.annotation.RequestUserReferenceId;
import club.gach_dong.exception.UserException.UserNotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
public class UserReferenceIdParameterResolver implements HandlerMethodArgumentResolver {

    private static final String REFERENCE_ID_HEADER_KEY = "X-USER-REFERENCE-ID";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(RequestUserReferenceId.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String referenceId = request.getHeader(REFERENCE_ID_HEADER_KEY);

        if (referenceId != null) {
            return referenceId;
        }
        throw new UserNotFound();
    }
}
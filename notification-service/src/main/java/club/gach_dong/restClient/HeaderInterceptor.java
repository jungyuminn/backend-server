package club.gach_dong.restClient;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class HeaderInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest httpRequest = attributes.getRequest();
            Enumeration<String> headerNames = httpRequest.getHeaderNames();

            String authorization = httpRequest.getHeader("Authorization");
            if (authorization != null) {
                request.getHeaders().set("Authorization", authorization);
            }

            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                if (headerName.equalsIgnoreCase("reqq-id")) {
                    String reqqIdValue = httpRequest.getHeader(headerName);
                    request.getHeaders().set("reqq-id", reqqIdValue);
                    break;
                }
            }
        }

        return execution.execute(request, body);
    }
}

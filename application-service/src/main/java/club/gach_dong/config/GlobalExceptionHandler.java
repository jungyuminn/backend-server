package club.gach_dong.config;


import club.gach_dong.exception.DomainException;
import club.gach_dong.response.ErrorResponse;
import club.gach_dong.response.status.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException exception) {
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.fromErrorCode(exception.getErrorCode()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> validException(Exception ex) {
        String errorMessage = "입력값 검증 오류: ";
        if (ex instanceof MethodArgumentNotValidException mex) {
            errorMessage += mex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        } else if (ex instanceof ConstraintViolationException cvex) {
            errorMessage += cvex.getConstraintViolations().iterator().next().getMessage();
        } else {
            errorMessage += "알 수 없는 오류";
        }
        ErrorResponse response = new ErrorResponse(
                ErrorCode.REQUEST_INPUT_NOT_VALID.getCode(),
                errorMessage
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(HttpServletRequest request, Exception exception) {
        String method = request.getMethod();
        String userAgent = request.getHeader("User-Agent");
        String fullPath = request.getRequestURL().toString();
        String originIp = extractOriginIp(request);
//        String requestBody = extractRequestBody(request);

        logException(method, fullPath, originIp, userAgent, exception);

        String errorMessage = Optional.ofNullable(exception.getMessage())
                .orElse("서버 내부 오류가 발생했습니다. 관리자에게 문의하세요.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    private String extractOriginIp(HttpServletRequest request) {
        String proxyIp = request.getHeader("X-Forwarded-For");
        return Optional.ofNullable(proxyIp)
                .orElseGet(() -> request.getRemoteAddr() != null ? request.getRemoteAddr() : "UNKNOWN SOURCE");
    }

    private String extractRequestBody(HttpServletRequest request) {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            logger.error("Failed to extract request body", e);
        }
        return requestBody.toString();
    }

    private void logException(String method, String fullPath, String originIp, String userAgent, Exception exception) {
        logger.error("Exception occurred: {} {} {} ERROR {} {}", method, fullPath, originIp, exception.getMessage(),
                userAgent);
    }
}
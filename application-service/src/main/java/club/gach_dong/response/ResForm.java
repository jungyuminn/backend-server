package club.gach_dong.response;


import club.gach_dong.response.status.InSuccess;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"time", "code", "message", "result"})
public class ResForm<T> {

    //    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
//    private final LocalDateTime time;
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    //Returns
    public static <T> ResForm<T> onSuccess(InSuccess inSuccess, T result) {
        return new ResForm<>(
//                LocalDateTime.now(),
                inSuccess.getCode(),
                inSuccess.getMessage(),
                result
        );
    }
//
//    // 실패한 경우 응답 생성
//    public static <T> ResForm<T> error(String code, String message, T result) {
//        return new ResForm<>(LocalDateTime.now(), code, message, result);
//    }

}

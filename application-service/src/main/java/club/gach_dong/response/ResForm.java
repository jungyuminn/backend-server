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
    
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    //Returns
    public static <T> ResForm<T> onSuccess(InSuccess inSuccess, T result) {
        return new ResForm<>(
                inSuccess.getCode(),
                inSuccess.getMessage(),
                result
        );
    }
}

package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;

public record ArrayResponse<T>(
        @Schema(description = "결과 목록", nullable = false)
        Collection<T> results
) {
    public static <T> ArrayResponse<T> of(Collection<T> results) {
        return new ArrayResponse<>(results);
    }
}

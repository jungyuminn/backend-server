package club.gach_dong.club.dto.response;

import java.util.Collection;

public record ArrayResponse<T>(
        Collection<T> results
) {
    public static <T> ArrayResponse<T> of(Collection<T> results) {
        return new ArrayResponse<>(results);
    }
}

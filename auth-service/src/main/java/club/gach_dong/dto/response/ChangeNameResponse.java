package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChangeNameResponse(
        @Schema(description = "사용자 참조 ID", example = "user12345")
        String userReferenceId,

        @Schema(description = "변경된 사용자 이름", example = "변경한 이름")
        String newName
) {
    public static ChangeNameResponse of(String userReferenceId, String newName) {
        return new ChangeNameResponse(userReferenceId, newName);
    }
}

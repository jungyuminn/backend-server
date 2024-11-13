package club.gach_dong.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProfilesRequest(
        @NotNull
        @Schema(description = "UUID 목록", example = "[\"uuid1\", \"uuid2\", \"uuid3\"]")
        List<String> userReferenceId
) {}

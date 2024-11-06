package club.gach_dong.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Map;
import lombok.Getter;

public class ApplicationRequestDTO {

    @Schema(description = "지원서 양식 생성 요청 DTO")
    @Getter
    public static class ToCreateApplicationFormDTO {
        @Schema(description = "지원 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "3")
        @NotNull(message = "ApplyId가 누락되었습니다.")
        private Long applyId;

        @Schema(description = "지원서 양식 상태(임시저장/저장)", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "SAVED")
        @NotNull(message = "지원 양식 상태가 누락되었습니다.")
        @Pattern(regexp = "TEMPORARY_SAVED|SAVED", message = "지원 양식 상태는 TEMPORARY_SAVED 또는 SAVED 이어야 합니다.")
        private String status;

        @Schema(description = "지원서 양식 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "가츠동 지원서 양식")
        @NotNull(message = "지원서 이름이 누락되었습니다.")
        @Size(max = 50, message = "지원서 이름은 50자 이내여야 합니다.")
        private String formName;

        @Schema(
                description = "지원서 양식 본문, Json 형식으로 넣어주세요.",
                requiredMode = Schema.RequiredMode.REQUIRED,
                nullable = false,
                example = "{\n" +
                        "  \"name\": \"이름을 넣어주세요\",\n" +
                        "  \"age\": \"나이를 넣어주세요\",\n" +
                        "  \"education\": {\n" +
                        "    \"university\": \"출신 학교를 넣어주세요\",\n" +
                        "    \"major\": \"전공을 넣어주세요\"\n" +
                        "  }\n" +
                        "}"
        )
        @NotNull(message = "지원서 본문이 누락되었습니다.")
        @Size(max = 2000, message = "지원서 본문이 너무 큽니다.")
        @JsonProperty("formBody")
        private Map<String, Object> formBody;
    }

    @Schema(description = "동아리 지원 요청 DTO")
    @Getter
    public static class ToApplyClubDTO {
        @Schema(description = "지원 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "3")
        @NotNull(message = "지원서 Id가 누락되었습니다.")
        private Long applicationFormId;

        @Schema(
                description = "지원서 답변 본문, Json 형식으로 넣어주세요.",
                requiredMode = Schema.RequiredMode.REQUIRED,
                nullable = false,
                example = "{\n" +
                        "  \"name\": \"가츠동\",\n" +
                        "  \"age\": 25,\n" +
                        "  \"education\": {\n" +
                        "    \"university\": \"가천대학교\",\n" +
                        "    \"major\": \"컴퓨터공학\"\n" +
                        "  }\n" +
                        "}"
        )
        @Size(max = 30000, message = "지원서 답변이 너무 큽니다.")
        @JsonProperty("formBody")
        private Map<String, Object> formBody;

        @Schema(description = "지원서 상태(임시저장/저장/수정 가능)", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "SAVED")
        @NotNull(message = "지원 상태가 누락되었습니다.")
        @Pattern(regexp = "TEMPORARY_SAVED|SAVED|SAVED_CHANGEABLE", message = "지원 상태는 TEMPORARY_SAVED 또는 SAVED, SAVED_CHANGEABLE 이어야 합니다.")
        private String status;

        @Schema(description = "지원할 동아리 이름", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "가츠동")
        @NotNull(message = "동아리 이름이 누락되었습니다.")
        @Size(max = 80, message = "동이리 이름이 너무 큽니다.")
        private String clubName;
    }

    @Schema(description = "동아리 지원자 상태 수정 요청 DTO")
    @Getter
    public static class ToChangeApplicationStatus {
        @Schema(description = "변경할 지원 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "2")
        @NotNull(message = "변경할 Application Id가 누락되었습니다.")
        private Long applicationId;

        @Schema(description = "변경할 지원 상태", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false, example = "서류 합격")
        @NotNull(message = "변경할 application 상태를 입력해 주세요.")
        @Size(max = 80, message = "상태가 너무 깁니다.")
        private String status;
    }
}

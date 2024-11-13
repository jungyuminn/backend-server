package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

public class ApplicationResponseDTO {
    @Getter
    @Builder
    @Schema(description = "관리자용 양식 조회 결과 반환 DTO")
    public static class ToGetFormInfoAdminDTO {
        @Schema(description = "지원서 양식 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Long formId;

        @Schema(description = "지원서 양식 이름", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String formName;

        @Schema(description = "지원서 양식 내용(Json)", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Map<String, Object> formBody;

        @Schema(description = "지원서 양식 상태 (임시 저장인지, 삭제 가능한지 등)", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String formStatus;
    }

    @Getter
    @Builder
    @Schema(description = "사용자용 양식 조회 결과 반환 DTO")
    public static class ToGetFormInfoUserDTO {
        @Schema(description = "지원서 양식 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Long formId;

        @Schema(description = "지원서 양식 이름", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String formName;

        @Schema(description = "지원서 양식 내용(Json)", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Map<String, Object> formBody;
    }

    @Getter
    @Builder
    @Schema(description = "지원 내역 목록 조회 결과 반환 DTO")
    public static class ToGetApplicationHistoryListDTO {
        private List<ToGetApplicationHistoryDTO> toGetApplicationHistoryDTO;
    }

    @Getter
    @Builder
    @Schema(description = "지원 내역 상세 조회 결과 반환 DTO")
    public static class ToGetApplicationHistoryDTO {
        @Schema(description = "지원 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Long applicationId;

        @Schema(description = "지원한 동아리 이름", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String clubName;

        @Schema(description = "지원 상태(합격, 불합격, 서류 합격 등)", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String status;

        @Schema(description = "지원한 날짜", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private LocalDateTime submitDate;
    }

    @Getter
    @Builder
    @Schema(description = "지원서 양식 생성 결과 반환 DTO")
    public static class ToCreateApplicationFormDTO {
        @Schema(description = "생성된 지원서 양식 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Long applicationFormId;
    }

    @Getter
    @Builder
    @Schema(description = "동아리 지원 결과 반환 DTO")
    public static class ToCreateApplicationDTO {
        @Schema(description = "접수된 지원 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Long applyId;
    }

    @Getter
    @Builder
    @Schema(description = "관리자용 동아리 지원 리스트 반환 DTO")
    public static class ToGetApplicationListAdminDTO {
        @Schema(description = "접수된 지원 목록 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private List<ToGetApplicationDTO> toGetApplicationDTO;
    }

    @Getter
    @Builder
    @Schema(description = "관리자용 지원 내역 상세 조회 결과 반환 DTO")
    public static class ToGetApplicationDTO {
        @Schema(description = "지원 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Long applicationId;

        @Schema(description = "지원자 ID", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String userReferenceId;

        @Schema(description = "지원자 email", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String userEmail;

        @Schema(description = "지원자 이름", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String userName;

        @Schema(description = "지원자 프로필 이미지", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String userProfileUrl;

        @Schema(description = "지원 상태(합격, 불합격, 서류 합격 등)", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private String status;

        @Schema(description = "지원한 날짜", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private LocalDateTime submitDate;

        @Schema(description = "답변 내용", requiredMode = Schema.RequiredMode.REQUIRED, nullable = false)
        private Map<String, Object> applicationBody;
    }
}

package club.gach_dong.dto.response;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

public class ApplicationResponseDTO {
    @Getter
    @Builder
    public static class ToGetFormInfoAdminDTO {
        private Long formId;
        private String formName;
        private Map<String, Object> formBody;
        private String formStatus;
    }

    @Getter
    @Builder
    public static class ToGetFormInfoUserDTO {
        private Long formId;
        private String formName;
        private Map<String, Object> formBody;
    }

    @Getter
    @Builder
    public static class ToGetApplicationHistoryListDTO {
        private List<ToGetApplicationHistoryDTO> toGetApplicationHistoryDTO;
    }

    @Getter
    @Builder
    public static class ToGetApplicationHistoryDTO {
        private Long applicationId;
        private String clubName;
        private String status;
    }

    @Getter
    @Builder
    public static class ToCreateApplicationFormDTO {
        private Long applicationFormId;
    }

    @Getter
    @Builder
    public static class ToCreateApplicationDTO {
        private Long applyId;
    }

}

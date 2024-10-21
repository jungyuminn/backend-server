package club.gach_dong.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class ApplicationRequestDTO {

    @Getter
    public static class ToCreateApplicationFormDTO{
        @NotNull(message = "ClubId가 누락되었습니다.")
        private Long clubId;

        @NotNull(message = "지원서 상태가 누락되었습니다.")
        @Pattern(regexp = "TEMP|NOTUSE", message = "지원서 상태는 TEMP 또는 NOTUSE 이어야 합니다.")
        private String status;

        @NotNull(message = "지원서 이름이 누락되었습니다.")
        @Size(max = 50, message = "지원서 이름은 50자 이내여야 합니다.")
        private String formName;

        @NotNull(message = "지원서 본문이 누락되었습니다.")
        @Size(max = 2000, message = "지원서 본문이 너무 큽니다.")
        private String formBody;
    }

    @Getter
    public static class ToApplyClub{
        @NotNull(message = "동아리 모집 Id가 누락되었습니다.")
        private Long applyId;

        @NotNull(message = "지원서 Id가 누락되었습니다.")
        private Long applicationFormId;

        @Size(max = 30000, message = "지원서 답변이 너무 큽니다.")
        private String formBody;
    }
}

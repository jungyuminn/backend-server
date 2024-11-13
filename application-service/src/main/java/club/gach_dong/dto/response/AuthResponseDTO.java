package club.gach_dong.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

public class AuthResponseDTO {

    @Getter
    @Builder
    @Schema(description = "내부 Service Mesh용 DTO (FrontEnd와는 관계가 없습니다.)")
    public static class getUserProfile {
        @Schema(description = "사용자 ID", example = "654fdh46-658fdg")
        String userReferenceId;

        @Schema(description = "사용자 이메일", example = "user@gachon.ac.kr")
        String email;

        @Schema(description = "사용자 이름", example = "홍길동")
        String name;

        @Schema(description = "사용자 권한", example = "USER, ADMIN")
        String role;

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.png")
        String profileImageUrl;
    }

}

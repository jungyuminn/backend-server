package club.gach_dong.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.gach_dong.dto.response.InviteCodeRegisterResponse;
import club.gach_dong.dto.response.InviteCodeResponse;
import club.gach_dong.annotation.RequestUserReferenceId;

@Tag(name = "관리자 서비스 API", description = "관리자 서비스 관련 API")
@RestController
@RequestMapping("/api/v1")
public interface AdminApiSpecification {

    @Operation(summary = "초대 코드 생성",
            description = "동아리의 초대 코드를 생성합니다.",
            security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/create/invite-code")
    ResponseEntity<InviteCodeResponse> createInviteCode(
            @Parameter(hidden = true)
            @RequestUserReferenceId String userReferenceId,
            @RequestParam Long clubId
    );

    @Operation(summary = "초대 코드 등록",
            description = "동아리의 초대 코드를 등록하여 해당 동아리의 관리자 권한을 얻습니다.",
            security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/register/invite-code")
    ResponseEntity<InviteCodeRegisterResponse> registerInviteCode(
            @RequestParam String inviteCode,
            @Parameter(hidden = true)
            @RequestUserReferenceId String userReferenceId
    );
}

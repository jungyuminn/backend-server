package club.gach_dong.api;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.gach_dong.dto.request.InviteCodeRequest;

@Tag(name = "동아리 관리자 API", description = "동아리 관리자 관련 API")
public interface AdminApiSpecification {

    @Operation(summary = "동아리 이름 조회", description = "사용자가 생성한 동아리의 이름을 반환합니다.")
    @GetMapping("/club")
    ResponseEntity<String> getClubName(
            @Parameter(description = "사용자의 ID") @RequestHeader("X-MEMBER-ID") String userReferenceId);

    @Operation(summary = "동아리 관리자 권한 이전", description = "A 관리자가 B 유저에게 동아리 권한을 이전합니다.")
    @PostMapping("/transfer-club")
    ResponseEntity<String> transferClubAdmin(
            @Parameter(description = "권한 이전 대상의 사용자 ID") @Valid @RequestParam String targetUserReferenceId,
            @Parameter(description = "사용자의 ID") @RequestHeader("X-MEMBER-ID") String userReferenceId);

    @Operation(summary = "초대코드 생성", description = "사용자가 자신의 동아리에 대한 초대코드를 생성합니다.")
    @PostMapping("/invite-code/generate")
    ResponseEntity<String> generateInviteCode(
            @Parameter(description = "사용자의 ID") @RequestHeader("X-MEMBER-ID") String userReferenceId);

    @Operation(summary = "초대코드 등록", description = "초대코드를 사용하여 동아리에 가입합니다.")
    @PostMapping("/invite-code/register")
    ResponseEntity<String> registerInviteCode(
            @Parameter(description = "초대코드 등록 정보") @Valid @RequestBody InviteCodeRequest inviteCodeRequest);
}

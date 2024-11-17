package club.gach_dong.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.gach_dong.dto.request.ChangePasswordRequest;
import club.gach_dong.dto.response.ChangeNameResponse;
import club.gach_dong.dto.response.TokenResponse;
import club.gach_dong.dto.response.UserProfileResponse;

@Tag(name = "관리자 인증/인가 API", description = "관리자 인증 및 인가 관련 API")
@RestController
@RequestMapping("admin/api/v1")
public interface AdminAuthApiSpecification {

    @Operation(summary = "비밀번호 변경", description = "기존 비밀번호를 변경합니다.",
            security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/change-password")
    ResponseEntity<String> changePassword(
            @Parameter(description = "JWT 토큰") @RequestHeader("Authorization") String token,
            @Parameter(description = "비밀번호 변경 정보") @Valid @RequestBody ChangePasswordRequest changePasswordRequest);

    @Operation(summary = "로그아웃", description = "사용자를 로그아웃합니다.",
            security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/logout")
    ResponseEntity<String> logout(
            @Parameter(description = "JWT 토큰") @RequestHeader("Authorization") String token,
            @Parameter(description = "Refresh Token") @RequestHeader("Refresh-Token") String refreshToken);

    @Operation(summary = "회원탈퇴", description = "사용자의 계정을 삭제합니다.",
            security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/unregister")
    ResponseEntity<String> deleteAccount(
            @Parameter(description = "JWT 토큰") @RequestHeader("Authorization") String token);

    @Operation(summary = "회원 정보 조회", description = "사용자의 프로필 정보를 조회합니다.",
            security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/profile")
    ResponseEntity<UserProfileResponse> getProfile(
            @Parameter(description = "JWT 토큰") @RequestHeader("Authorization") String token);

    @Operation(summary = "Refresh Token 재발급", description = "유효한 Refresh Token을 사용하여 새로운 Refresh Token과 Access Token을 발급받습니다.",
            security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/refresh-token")
    ResponseEntity<TokenResponse> refreshToken(
            @Parameter(description = "Refresh Token") @RequestHeader("Authorization") String refreshToken);

    @Operation(summary = "이름 변경", description = "사용자의 이름을 변경합니다.",
            security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/change-name")
    ResponseEntity<ChangeNameResponse> changeName(
            @Parameter(description = "JWT 토큰") @RequestHeader("Authorization") String token,
            @Parameter(description = "변경할 새로운 이름") @RequestParam String newName);
}
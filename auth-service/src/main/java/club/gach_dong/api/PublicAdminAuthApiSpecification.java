package club.gach_dong.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import club.gach_dong.dto.request.LoginRequest;
import club.gach_dong.dto.request.RegistrationRequest;
import club.gach_dong.dto.response.AuthResponse;

@Tag(name = "Public 관리자 인증/인가 API", description = "Public한 관리자 인증 및 인가 관련 API")
@RestController
@RequestMapping("/public/api/v1")
public interface PublicAdminAuthApiSpecification {

    @Operation(summary = "회원가입", description = "회원가입을 완료합니다. \n" +
            "- email: 사용자 이메일 (gachon.ac.kr 도메인) \n" +
            "- password: 8~16자 영문, 숫자, 특수문자를 포함한 비밀번호 \n" +
            "- name: 사용자 이름 \n" +
            "- role: ADMIN, USER")
    @PostMapping("/admin/register")
    ResponseEntity<String> completeRegistration(
            @Parameter(description = "회원가입 정보") @Valid @RequestBody RegistrationRequest registrationRequest);

    @Operation(summary = "사용자 로그인", description = "사용자가 로그인합니다.")
    @PostMapping("/admin/login")
    ResponseEntity<AuthResponse> login(
            @Parameter(description = "로그인 정보") @Valid @RequestBody LoginRequest loginRequest);

    @Operation(summary = "비밀번호 재발급", description = "이메일 인증 코드를 입력하여 임시 비밀번호를 재발급합니다.")
    @PostMapping("/admin/reset-password")
    ResponseEntity<String> resetPassword(
            @Parameter(description = "사용자의 이메일 주소") @RequestParam String email,
            @Parameter(description = "인증 코드") @RequestParam String code);
}

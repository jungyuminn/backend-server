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

@Tag(name = "Public 사용자 인증/인가 API", description = "Public한 사용자 인증 및 인가 관련 API")
@RestController
@RequestMapping("/public/api/v1")
public interface PublicAuthApiSpecification {

    @Operation(summary = "이메일 인증 코드 발송", description = "이메일로 유효시간 3분의 6자리의 인증 코드를 발송합니다.")
    @PostMapping("/send-verification-code")
    ResponseEntity<String> sendVerificationCode(
            @Parameter(description = "사용자의 이메일 주소") @RequestParam String email);

    @Operation(summary = "회원가입용 인증 코드 발송", description = "이메일로 회원가입을 위한 유효시간 3분의 6자리의 인증 코드를 발송합니다.")
    @PostMapping("/send-registration-verification-code")
    ResponseEntity<String> sendRegistrationVerificationCode(
            @Parameter(description = "회원가입을 위한 이메일 주소") @RequestParam String email);

    @Operation(summary = "회원가입", description = "회원가입을 완료합니다. \n" +
            "- email: 사용자 이메일 (gachon.ac.kr 도메인) \n" +
            "- password: 8~16자 영문, 숫자, 특수문자를 포함한 비밀번호 \n" +
            "- name: 사용자 이름 \n" +
            "- role: ADMIN, USER")
    @PostMapping("/register")
    ResponseEntity<String> completeRegistration(
            @Parameter(description = "회원가입 정보") @Valid @RequestBody RegistrationRequest registrationRequest);

    @Operation(summary = "사용자 로그인", description = "사용자가 로그인합니다.")
    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(
            @Parameter(description = "로그인 정보") @Valid @RequestBody LoginRequest loginRequest);

    @Operation(summary = "비밀번호 재발급", description = "이메일 인증 코드를 입력하여 임시 비밀번호를 재발급합니다.")
    @PostMapping("/reset-password")
    ResponseEntity<String> resetPassword(
            @Parameter(description = "사용자의 이메일 주소") @RequestParam String email,
            @Parameter(description = "인증 코드") @RequestParam String code);

    @Operation(summary = "인증 코드 검증", description = "사용자가 입력한 인증 코드를 검증합니다.")
    @PostMapping("/verify-code")
    ResponseEntity<String> verifyCode(
            @Parameter(description = "사용자의 이메일 주소") @RequestParam String email,
            @Parameter(description = "인증 코드") @RequestParam String code);
}

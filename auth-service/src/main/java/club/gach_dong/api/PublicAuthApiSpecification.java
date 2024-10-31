package club.gach_dong.api;

import club.gach_dong.dto.AuthResponse;
import club.gach_dong.dto.LoginDto;
import club.gach_dong.dto.RegistrationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Public 인증/인가 API", description = "Public한 인증/인가가 필요한 API")
@RestController
@RequestMapping("/public/api/v1")
public interface PublicAuthApiSpecification {

    @Operation(summary = "이메일 인증 코드 발송", description = "이메일로 유효시간 3분의 6자리의 인증 코드를 발송합니다.")
    @PostMapping("/send_verification_code")
    ResponseEntity<String> sendVerificationCode(
            @Parameter(description = "사용자의 이메일 주소") @RequestParam String email);

    @Operation(summary = "회원가입", description = "회원가입을 완료합니다.")
    @PostMapping("/register")
    ResponseEntity<String> completeRegistration(
            @Parameter(description = "회원가입 정보") @Valid @RequestBody RegistrationDto registrationDto);

    @Operation(summary = "사용자 로그인", description = "사용자가 로그인합니다.")
    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(
            @Parameter(description = "로그인 정보") @Valid @RequestBody LoginDto loginDto);

    @Operation(summary = "비밀번호 재발급", description = "이메일 인증 코드를 입력하여 임시 비밀번호를 재발급합니다.")
    @PostMapping("/reset_password")
    ResponseEntity<String> resetPassword(
            @Parameter(description = "사용자의 이메일 주소") @RequestParam String email,
            @Parameter(description = "인증 코드") @RequestParam String code);
}
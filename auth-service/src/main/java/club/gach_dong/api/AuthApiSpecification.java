package club.gach_dong.api;

import club.gach_dong.dto.AuthResponse;
import club.gach_dong.dto.ChangePasswordDto;
import club.gach_dong.dto.LoginDto;
import club.gach_dong.dto.RegistrationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증/인가 API", description = "사용자 인증 및 인가 관련 API")
@RestController
@RequestMapping("/api/v1")
public interface AuthApiSpecification {

    @Operation(summary = "이메일 인증 코드 발송", description = "이메일로 유효시간 3분의 6자리의 인증 코드를 발송합니다.")
    @PostMapping("/send_verification_code")
    ResponseEntity<String> sendVerificationCode(
            @Parameter(description = "사용자의 이메일 주소") @RequestParam String email);

    @Operation(
            summary = "회원가입",
            description = "회원가입을 완료합니다. \n" +
                    "- email: 사용자 이메일 (gachon.ac.kr 도메인 고정) \n" +
                    "- code: 6자리의 이메일 인증 코드 \n" +
                    "- password: 사용자 비밀번호 (8~16자, 영문, 숫자, 특수문자 포함) \n" +
                    "- name: 사용자 이름 \n" +
                    "- role: 사용자 역할 (USER, ADMIN) "
    )
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

    @Operation(summary = "비밀번호 변경", description = "기존 비밀번호를 변경합니다.")
    @PostMapping("/change_password")
    ResponseEntity<String> changePassword(
            @Parameter(description = "JWT 토큰") @RequestHeader("Authorization") String token,
            @Parameter(description = "비밀번호 변경 정보") @Valid @RequestBody ChangePasswordDto changePasswordDto);

    @Operation(summary = "로그아웃", description = "사용자를 로그아웃합니다.")
    @PostMapping("/logout")
    ResponseEntity<String> logout(
            @Parameter(description = "JWT 토큰") @RequestHeader("Authorization") String token);

    @Operation(summary = "회원탈퇴", description = "사용자의 계정을 삭제합니다.")
    @PostMapping("/unregister")
    ResponseEntity<String> deleteAccount(
            @Parameter(description = "JWT 토큰") @RequestHeader("Authorization") String token);
}

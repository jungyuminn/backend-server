package club.gach_dong.controller;


import club.gach_dong.annotation.RequestUserReferenceId;
import club.gach_dong.dto.response.AuthResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MockUpController {

    @GetMapping("/authTest")
    public Boolean testAuth(@RequestParam int applyId, HttpServletRequest httpServletRequest,
                            @RequestUserReferenceId String userReferenceId) {
        System.out.println("통신 체결");
        String headerValue = httpServletRequest.getHeader("reqq-id");
        System.out.println("reqq-id: " + headerValue);
        System.out.println("UserID: " + userReferenceId);
        System.out.println("applyId: " + applyId);
        return true;
    }

    @GetMapping("/validApplyTest/{applyId}")
    public Boolean testAuth(@PathVariable Long applyId, HttpServletRequest httpServletRequest) {
        System.out.println("통신 체결 apply is valid");
        String headerValue = httpServletRequest.getHeader("reqq-id");
        System.out.println("reqq-id: " + headerValue);
        System.out.println("applyId: " + applyId);
        return true;
    }

    @PostMapping("/profile")
    public List<AuthResponseDTO.getUserProfile> getUserProfiles(@RequestBody Map<String, List<String>> request) {
        System.out.println("통신 체결 apply is valid");

        List<String> userReferenceIds = request.get("userReferenceId");
        if (userReferenceIds == null || userReferenceIds.isEmpty()) {
            throw new IllegalArgumentException("userReferenceId list is required");
        }

        return userReferenceIds.stream()
                .map(id -> AuthResponseDTO.getUserProfile.builder()
                        .userReferenceId(id)
                        .profileImageUrl("http://testtesttest")
                        .email("test@gachon.ac.kr")
                        .name("tester")
                        .role("USER")
                        .build())
                .collect(Collectors.toList());
    }

    @Getter
    @Builder
    public static class testUserDetailDTO {
        @Schema(description = "사용자 이메일", example = "user@gachon.ac.kr")
        String email;

        @Schema(description = "사용자 이름", example = "홍길동")
        String name;

        @Schema(description = "사용자 권한", example = "USER, ADMIN")
        String role;

        @Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.png")
        String profileImageUrl;

    }

//    @DeleteMapping("/test")
//    public void deleteApply() {
//        objectStorageService.deleteObject("ApplicationDocs", "testImg");
//    }
}

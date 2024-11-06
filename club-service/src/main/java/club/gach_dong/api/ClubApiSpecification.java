package club.gach_dong.api;

import club.gach_dong.annotation.RequestUserReferenceId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "동아리 API", description = "동아리 관련 API")
@RestController
@RequestMapping("/api/v1")
@Validated
public interface ClubApiSpecification {
    @Operation(
            summary = "테스트 조회",
            description = "테스트입니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    @GetMapping("/user-info")
    String getUserInfo(
            @RequestUserReferenceId
            String userReferenceId
    );
}
package club.gach_dong.api;


import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "동아리 API", description = "동아리 관련 API")
@RestController
@RequestMapping("/api/v1")
@Validated
public interface ClubApiSpecification {
    @Operation(
            summary = "동아리 생성",
            description = "동아리 정보를 입력받아 동아리를 생성합니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    @PostMapping("/create")
    ResponseEntity<ClubResponse> createClub(
            @Valid
            @RequestBody
            CreateClubRequest createClubRequest
    );
}
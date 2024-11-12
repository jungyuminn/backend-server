package club.gach_dong.api;

import club.gach_dong.annotation.RequestUserReferenceId;
import club.gach_dong.dto.request.CreateClubContactInfoRequest;
import club.gach_dong.dto.request.CreateClubActivityRequest;
import club.gach_dong.dto.request.CreateClubRecruitmentRequest;
import club.gach_dong.dto.request.CreateClubRequest;
import club.gach_dong.dto.response.AdminAuthorizedClubResponse;
import club.gach_dong.dto.response.ArrayResponse;
import club.gach_dong.dto.response.CreateClubActivityResponse;
import club.gach_dong.dto.response.CreateClubContactInfoResponse;
import club.gach_dong.dto.response.CreateClubRecruitmentResponse;
import club.gach_dong.dto.response.ClubResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin 동아리 API", description = "동아리 관리자 관련 API")
@RestController
@RequestMapping("/admin/api/v1")
@Validated
public interface ClubAdminApiSpecification {
    @Operation(
            summary = "동아리 생성",
            description = "동아리 정보를 입력받아 동아리를 생성합니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    @PostMapping("/create")
    ResponseEntity<ClubResponse> createClub(
            @RequestUserReferenceId
            String userReferenceId,
            @Valid
            @RequestBody
            CreateClubRequest createClubRequest
    );

    @Operation(
            summary = "동아리 활동 내역 생성",
            description = "동아리 활동 내역을 입력받아 동아리에 추가합니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    @PostMapping("/activities/create")
    ResponseEntity<CreateClubActivityResponse> createClubActivity(
            @RequestUserReferenceId
            String userReferenceId,
            @Valid
            @RequestBody
            CreateClubActivityRequest createClubActivityRequest
    );

    @Operation(
            summary = "동아리 연락처 정보 생성",
            description = "동아리 연락처 정보를 입력받아 동아리에 추가합니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    @PostMapping("/contact-info/create")
    ResponseEntity<CreateClubContactInfoResponse> createClubContactInfo(
            @RequestUserReferenceId
            String userReferenceId,
            @Valid
            @RequestBody
            CreateClubContactInfoRequest createClubContactInfoRequest
    );

    @Operation(
            summary = "동아리 모집 공고 생성",
            description = "동아리 모집 공고를 입력받아 동아리에 추가합니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    @PostMapping("/recruitment/create")
    ResponseEntity<CreateClubRecruitmentResponse> createClubRecruitment(
            @RequestUserReferenceId
            String userReferenceId,
            @Valid
            @RequestBody
            CreateClubRecruitmentRequest createClubRecruitmentRequest
    );

    @Operation(
            summary = "권한있는 동아리 리스트 조회",
            description = "권한이 있는 사용자의 동아리 리스트를 조회합니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    @GetMapping("/authorized-clubs")
    ArrayResponse<AdminAuthorizedClubResponse> getAuthorizedClubs(
            @RequestUserReferenceId
            String userReferenceId
    );

    @Operation(
            summary = "특정 동아리에 대한 권한이 있는지 확인",
            description = "특정 동아리에 대한 권한이 있는지 확인합니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    @GetMapping("/{clubId}/has-authority")
    Boolean hasAuthority(
            @RequestUserReferenceId
            String userReferenceId,
            @PathParam("clubId")
            Long clubId
    );

    @Operation(
            summary = "유효한 동아리 모집 공고인지 확인",
            description = "유효한 동아리 모집 공고인지 확인합니다.",
            security = @SecurityRequirement(name = "Authorization")
    )
    @GetMapping("/recruitment/{recruitmentId}/is-valid")
    Boolean isValidRecruitment(
            @RequestParam("recruitmentId")
            Long recruitmentId
    );
}

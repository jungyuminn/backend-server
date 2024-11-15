package club.gach_dong.api;

import club.gach_dong.annotation.RequestUserReferenceId;
import club.gach_dong.dto.request.ApplicationRequestDTO;
import club.gach_dong.dto.response.ApplicationResponseDTO;
import club.gach_dong.response.ResForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "지원 API(사용자)", description = "동아리 지원 관련 사용자용 API")
@RestController
@RequestMapping("/api/v1")
@Validated
public interface ApplicationApiSpecification {

    @Operation(summary = "사용자용 지원서 양식 조회 API", description = "지원서 양식 ID를 이용해 양식을 조회합니다.", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/form/{formId}")
    ResForm<ApplicationResponseDTO.ToGetFormInfoUserDTO> getFormInfoUser(@PathVariable("formId") Long formId,
                                                                         @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "사용자 지원 내역 목록 조회 API", description = "지원 내역 목록을 조회합니다.", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/list")
    ResForm<ApplicationResponseDTO.ToGetApplicationHistoryListDTO> getApplicationHistory(
            @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "동아리 지원 API", description = "동아리에 지원합니다.", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping(value = "/{recruitmentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResForm<ApplicationResponseDTO.ToCreateApplicationDTO> createApplication(
            @PathVariable("recruitmentId") Long recruitmentId,
            @RequestPart(value = "files", required = false) @Parameter(description = "업로드할 문서 리스트") List<MultipartFile> files,
            @RequestPart(value = "toApplyClub") @Parameter(description = "동아리 지원에 필요한 요청 데이터") @Valid ApplicationRequestDTO.ToApplyClubDTO toApplyClub,
            @RequestUserReferenceId String userReferenceId
    );

    @Operation(summary = "동아리 지원 수정 API", description = "동아리에 지원을 수정합니다.", security = @SecurityRequirement(name = "Authorization"))
    @PutMapping(value = "/{recruitmentId} ", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResForm<ApplicationResponseDTO.ToCreateApplicationDTO> changeApplication(
            @PathVariable("recruitmentId") Long recruitmentId,
            @RequestPart(value = "certificateDocs", required = true) @Parameter(description = "업로드할 문서 리스트") List<MultipartFile> certificateDocs,
            @RequestPart(value = "toApplyClub", required = true) @Parameter(description = "동아리 지원에 필요한 요청 데이터") @Valid ApplicationRequestDTO.ToApplyClubDTO toApplyClub,
            @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "사용자 지원 취소 API", description = "지원 ID를 이용해 지원을 취소합니다.", security = @SecurityRequirement(name = "Authorization"))
    @DeleteMapping("/apply/{recruitmentId}")
    ResForm<?> deleteApplication(@PathVariable("recruitmentId") Long recruitmentId,
                                 @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "사용자 임시저장 여부 및 내용 반환 API", description = "지원 ID를 이용해 해당 사용자가 해당 임시적으로 저장한 지원이 있는지 확인 및 내용을 반환합니다.", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/apply/{applyId}")
    ResForm<?> getTempApplication(@PathVariable("applyId") Long recruitmentId,
                                  @RequestUserReferenceId String userReferenceId);

//    @Operation(summary = "지원 상세 조회 API", description = "지원 ID를 이용해 지원 내역을 조회합니다.")
//    @DeleteMapping("/admin/{applicationId}")
//    ResForm<?> getApplication(@PathVariable("applicationId") Long applicationId, HttpServletRequest httpServletRequest);
}


package club.gach_dong.api;

import club.gach_dong.annotation.RequestUserReferenceId;
import club.gach_dong.dto.request.ApplicationRequestDTO;
import club.gach_dong.dto.response.ApplicationResponseDTO;
import club.gach_dong.response.ResForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "지원 API(관리자)", description = "동아리 지원 관련 관리자용 API")
@RestController
@RequestMapping("/admin/api/v1")
@Validated
public interface ApplicationAdminApiSpecification {

    @Operation(summary = "관리자용 지원서 양식 조회 API", description = "지원서 양식 ID를 이용해 양식을 조회합니다.", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/form/{formId}")
    ResForm<ApplicationResponseDTO.ToGetFormInfoAdminDTO> getFormInfoAdmin(@PathVariable("formId") Long formId,
                                                                           @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "동아리 지원서 양식 생성 요청 API", description = "동아리 지원서 양식을 생성합니다.", security = @SecurityRequirement(name = "Authorization"))
    @PostMapping("/form/create")
    ResForm<ApplicationResponseDTO.ToCreateApplicationFormDTO> createApplicationForm(
            @Valid @RequestBody ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO,
            @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "동아리 지원서 양식 수정 요청 API", description = "동아리 지원서 양식을 수정합니다.", security = @SecurityRequirement(name = "Authorization"))
    @PutMapping("/form/{form_id}")
    ResForm<ApplicationResponseDTO.ToCreateApplicationFormDTO> changeApplicationForm(
            @PathVariable("form_id") Long formId,
            @Valid @RequestBody ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO,
            @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "지원 양식 삭제 API", description = "지원서 양식 ID를 이용해 양식을 삭제합니다.", security = @SecurityRequirement(name = "Authorization"))
    @DeleteMapping("/form/{formId}")
    ResForm<?> deleteApplicationForm(@PathVariable("formId") Long formId,
                                     @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "사용자 지원 상태 변경 API", description = "지원 ID를 이용해 지원 상태를 변경합니다.", security = @SecurityRequirement(name = "Authorization"))
    @PutMapping("/status")
    ResForm<?> changeApplicationStatus(
            @Valid @RequestBody ApplicationRequestDTO.ToChangeApplicationStatus toChangeApplicationStatus,
            @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "지원 목록 조회 API", description = "지원 ID를(recruitmentId) 이용해 지원 목록을 조회합니다.", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/{applyId}")
    ResForm<?> getClubApplicationList(@PathVariable("applyId") Long applyId,
                                      @RequestUserReferenceId String userReferenceId);

    @Operation(summary = "지원 내역 단건 조회 API", description = "지원 ID를 이용해 지원 목록을 조회합니다.", security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("application/{applicationId}")
    ResForm<?> getClubApplication(@PathVariable("applicationId") Long applicationId,
                                  @RequestUserReferenceId String userReferenceId);
}

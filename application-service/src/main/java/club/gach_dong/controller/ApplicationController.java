package club.gach_dong.controller;

import club.gach_dong.api.ApplicationApiSpecification;
import club.gach_dong.dto.request.ApplicationRequestDTO;
import club.gach_dong.dto.response.ApplicationResponseDTO;
import club.gach_dong.response.ResForm;
import club.gach_dong.response.status.InSuccess;
import club.gach_dong.service.ApplicationService;
import club.gach_dong.service.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApplicationController implements ApplicationApiSpecification {

    public final ApplicationService applicationService;
    public final AuthorizationService authorizationService;

    @Override
    public ResForm<ApplicationResponseDTO.ToGetFormInfoAdminDTO> getFormInfoAdmin(Long formId, HttpServletRequest httpServletRequest) {
        Long userId = authorizationService.getUserId(httpServletRequest);
        ApplicationResponseDTO.ToGetFormInfoAdminDTO toGetFormInfoAdminDTO = applicationService.getFormInfoAdmin(formId, userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_GET_ADMIN_INFO, toGetFormInfoAdminDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToGetFormInfoUserDTO> getFormInfoUser(Long formId, HttpServletRequest httpServletRequest) {
        Long userId = authorizationService.getUserId(httpServletRequest);
        ApplicationResponseDTO.ToGetFormInfoUserDTO toGetFormInfoUserDTO = applicationService.getFormInfoUser(formId, userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_GET_USER_INFO, toGetFormInfoUserDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToGetApplicationHistoryListDTO> getaApplicationHistory(HttpServletRequest httpServletRequest) {
        Long userId = authorizationService.getUserId(httpServletRequest);
        ApplicationResponseDTO.ToGetApplicationHistoryListDTO toGetApplicationHistoryListDTO = applicationService.getApplicationHistoryList(userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_GET_USER_INFO, toGetApplicationHistoryListDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationFormDTO> createApplicationForm(ApplicationRequestDTO.ToCreateApplicationFormDTO createApplicationFormDTO, HttpServletRequest httpServletRequest) {
        Long userId = authorizationService.getUserId(httpServletRequest);
        ApplicationResponseDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO = applicationService.createApplicationForm(createApplicationFormDTO, userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_CREATED, toCreateApplicationFormDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationDTO> createApplication(Long applyId, List<MultipartFile> files, ApplicationRequestDTO.ToApplyClubDTO toApplyClub, HttpServletRequest httpServletRequest) {
        Long userId = authorizationService.getUserId(httpServletRequest);
        ApplicationResponseDTO.ToCreateApplicationDTO toCreateApplicationDTO = applicationService.createApplication(applyId, files, toApplyClub, userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_SUCCESS, toCreateApplicationDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationFormDTO> changeApplicationForm(Long formId, ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO, HttpServletRequest httpServletRequest) {
        Long userId = authorizationService.getUserId(httpServletRequest);
        ApplicationResponseDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO1 = applicationService.changeApplicationForm(formId, toCreateApplicationFormDTO, userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_CHANGED, toCreateApplicationFormDTO1);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationDTO> changeApplication(Long applyId, List<MultipartFile> certificateDocs, ApplicationRequestDTO.ToApplyClubDTO toApplyClub, HttpServletRequest httpServletRequest) {
        Long userId = authorizationService.getUserId(httpServletRequest);
        ApplicationResponseDTO.ToCreateApplicationDTO toChangeApplicationDTO = applicationService.changeApplication(applyId, certificateDocs, toApplyClub, userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_CHANGED, toChangeApplicationDTO);
    }

    @Override
    public ResForm<?> deleteApplicationForm(Long formId, HttpServletRequest httpServletRequest) {
        Long userId = authorizationService.getUserId(httpServletRequest);
        applicationService.deleteApplicationForm(formId, userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_DELETED, null);
    }

    @Override
    public ResForm<?> deleteApplication(Long applyId, HttpServletRequest httpServletRequest) {
        Long userId = authorizationService.getUserId(httpServletRequest);
        applicationService.deleteApplication(applyId, userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_DELETED, null);
    }

    @Override
    public ResForm<?> changeApplicationStatus(Long applyId, ApplicationRequestDTO.ToChangeApplicationStatus toChangeApplicationStatus, HttpServletRequest httpServletRequest) {
        Long userId = authorizationService.getUserId(httpServletRequest);
        applicationService.changeApplicationStatus(applyId, toChangeApplicationStatus);
        return ResForm.onSuccess(InSuccess.APPLICATION_STATUS_CHANGED, null);
    }

    ;
}

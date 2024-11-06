package club.gach_dong.controller;

import club.gach_dong.api.ApplicationAdminApiSpecification;
import club.gach_dong.dto.request.ApplicationRequestDTO;
import club.gach_dong.dto.response.ApplicationResponseDTO;
import club.gach_dong.response.ResForm;
import club.gach_dong.response.status.InSuccess;
import club.gach_dong.service.ApplicationService;
import club.gach_dong.service.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApplicationAdminController implements ApplicationAdminApiSpecification {


    public final ApplicationService applicationService;
    public final AuthorizationService authorizationService;

    @Override
    public ResForm<ApplicationResponseDTO.ToGetFormInfoAdminDTO> getFormInfoAdmin(Long formId,
                                                                                  HttpServletRequest httpServletRequest) {
        String userId = authorizationService.getUserId(httpServletRequest);
        ApplicationResponseDTO.ToGetFormInfoAdminDTO toGetFormInfoAdminDTO = applicationService.getFormInfoAdmin(formId,
                userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_GET_ADMIN_INFO, toGetFormInfoAdminDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationFormDTO> createApplicationForm(
            ApplicationRequestDTO.ToCreateApplicationFormDTO createApplicationFormDTO,
            HttpServletRequest httpServletRequest) {
        String userId = authorizationService.getUserId(httpServletRequest);
        ApplicationResponseDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO = applicationService.createApplicationForm(
                createApplicationFormDTO, userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_CREATED, toCreateApplicationFormDTO);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationFormDTO> changeApplicationForm(Long formId,
                                                                                            ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO,
                                                                                            HttpServletRequest httpServletRequest) {
        String userId = authorizationService.getUserId(httpServletRequest);
        ApplicationResponseDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO1 = applicationService.changeApplicationForm(
                formId, toCreateApplicationFormDTO, userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_CHANGED, toCreateApplicationFormDTO1);
    }

    @Override
    public ResForm<?> deleteApplicationForm(Long formId, HttpServletRequest httpServletRequest) {
        String userId = authorizationService.getUserId(httpServletRequest);
        applicationService.deleteApplicationForm(formId, userId);
        return ResForm.onSuccess(InSuccess.APPLICATION_FORM_DELETED, null);
    }

    @Override
    public ResForm<?> changeApplicationStatus(ApplicationRequestDTO.ToChangeApplicationStatus toChangeApplicationStatus,
                                              HttpServletRequest httpServletRequest) {
        String userId = authorizationService.getUserId(httpServletRequest);
        applicationService.changeApplicationStatus(userId, toChangeApplicationStatus);
        return ResForm.onSuccess(InSuccess.APPLICATION_STATUS_CHANGED, null);
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToGetApplicationListAdminDTO> getClubApplicationList(Long applyId,
                                                                                               HttpServletRequest httpServletRequest) {
        String userId = authorizationService.getUserId(httpServletRequest);

        ApplicationResponseDTO.ToGetApplicationListAdminDTO toGetApplicationListAdminDTO = applicationService.getApplicationListAdmin(
                userId, applyId);

        return ResForm.onSuccess(InSuccess._OK, toGetApplicationListAdminDTO);
    }
}

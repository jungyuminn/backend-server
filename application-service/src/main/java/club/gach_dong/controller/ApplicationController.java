package club.gach_dong.controller;

import club.gach_dong.api.ApplicationApiSpecification;
import club.gach_dong.dto.request.ApplicationRequestDTO;
import club.gach_dong.dto.response.ApplicationResponseDTO;
import club.gach_dong.response.ResForm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ApplicationController implements ApplicationApiSpecification {
    @Override
    public ResForm<ApplicationResponseDTO.ToGetFormInfoAdminDTO> getFormInfoAdmin(Long drawId, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToGetFormInfoUserDTO> getFormInfoUser(Long drawId, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToGetApplicationHistoryListDTO> getFormInfoUser(HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationFormDTO> createApplicationForm(ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationDTO> createApplication(Long applyId, List<MultipartFile> certificateDocs, ApplicationRequestDTO.ToApplyClub toApplyClub, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationFormDTO> changeApplicationForm(ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResForm<ApplicationResponseDTO.ToCreateApplicationDTO> changeApplication(Long applyId, List<MultipartFile> certificateDocs, ApplicationRequestDTO.ToApplyClub toApplyClub, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResForm<?> deleteForm(Long formId, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResForm<?> deleteApplication(Long applyId, HttpServletRequest httpServletRequest) {
        return null;
    }

    ;
}

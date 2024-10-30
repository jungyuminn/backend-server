package club.gach_dong.service;

import club.gach_dong.domain.Application;
import club.gach_dong.domain.ApplicationDocs;
import club.gach_dong.domain.ApplicationForm;
import club.gach_dong.domain.ApplicationFormStatus;
import club.gach_dong.dto.request.ApplicationRequestDTO;
import club.gach_dong.dto.response.ApplicationResponseDTO;
import club.gach_dong.exception.CustomException;
import club.gach_dong.gcp.ObjectStorageService;
import club.gach_dong.gcp.ObjectStorageServiceConfig;
import club.gach_dong.repository.ApplicationDocsRepository;
import club.gach_dong.repository.ApplicationFormRepository;
import club.gach_dong.repository.ApplicationRepository;
import club.gach_dong.response.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationFormRepository applicationFormRepository;
    private final ApplicationRepository applicationRepository;
    private final FileService fileService;
    private final ObjectStorageService objectStorageService;
    private final ObjectStorageServiceConfig objectStorageServiceConfig;
    private final ApplicationDocsRepository applicationDocsRepository;
    private final AuthorizationService authorizationService;

    @Transactional
    public ApplicationResponseDTO.ToCreateApplicationFormDTO createApplicationForm(ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO, Long userId) {

        //Verify Club Admin Auth with Apply_Id, User_Id
        authorizationService.getAuthByUserIdAndApplyId(userId, toCreateApplicationFormDTO.getApplyId());

        ApplicationForm applicationForm = ApplicationForm.builder()
                .applicationFormStatus(ApplicationFormStatus.valueOf(toCreateApplicationFormDTO.getStatus()))
                .formName(toCreateApplicationFormDTO.getFormName())
                .applyId(toCreateApplicationFormDTO.getApplyId())
                .body(toCreateApplicationFormDTO.getFormBody())
                .build();

        ApplicationForm createdApplicationForm = applicationFormRepository.save(applicationForm);

        return ApplicationResponseDTO.ToCreateApplicationFormDTO.builder()
                .applicationFormId(createdApplicationForm.getId())
                .build();
    }

    @Transactional(readOnly = true)
    public ApplicationResponseDTO.ToGetFormInfoAdminDTO getFormInfoAdmin(Long formId, Long userId) {

        Optional<ApplicationForm> applicationFormOptional = applicationFormRepository.findById(formId);
        if (applicationFormOptional.isEmpty()) {
            throw new CustomException(ErrorStatus.APPLICATION_FORM_NOT_FOUND);
        }

        ApplicationForm applicationForm = applicationFormOptional.get();

        //Verify Club Admin Auth with Apply_Id, User_Id
        authorizationService.getAuthByUserIdAndApplyId(userId, applicationForm.getApplyId());

        return ApplicationResponseDTO.ToGetFormInfoAdminDTO.builder()
                .formId(applicationForm.getId())
                .formName(applicationForm.getFormName())
                .formBody(applicationForm.getBody())
                .formStatus(String.valueOf(applicationForm.getApplicationFormStatus()))
                .formSettings(null)
                .build();
    }

    @Transactional(readOnly = true)
    public ApplicationResponseDTO.ToGetFormInfoUserDTO getFormInfoUser(Long formId, Long userId) {

        Optional<ApplicationForm> applicationFormOptional = applicationFormRepository.findById(formId);
        if (applicationFormOptional.isEmpty()) {
            throw new CustomException(ErrorStatus.APPLICATION_FORM_NOT_FOUND);
        }

        ApplicationForm applicationForm = applicationFormOptional.get();

        return ApplicationResponseDTO.ToGetFormInfoUserDTO.builder()
                .formId(applicationForm.getId())
                .formName(applicationForm.getFormName())
                .formBody(applicationForm.getBody())
                .build();
    }

    @Transactional
    public void deleteApplicationForm(Long formId, Long userId) {

        Optional<ApplicationForm> applicationFormOptional = applicationFormRepository.findById(formId);
        if (applicationFormOptional.isEmpty()) {
            throw new CustomException(ErrorStatus.APPLICATION_FORM_NOT_FOUND);
        }

        ApplicationForm applicationForm = applicationFormOptional.get();

        //Verify Club Admin Auth with Apply_Id, User_Id
        authorizationService.getAuthByUserIdAndApplyId(userId, applicationForm.getApplyId());

        if (applicationForm.getApplicationFormStatus() == ApplicationFormStatus.IN_USE) {
            throw new CustomException(ErrorStatus.APPLICATION_FORM_IN_USE);
        }

        applicationFormRepository.deleteById(formId);
    }


    @Transactional
    public ApplicationResponseDTO.ToCreateApplicationFormDTO changeApplicationForm(Long formId, ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO, Long userId) {

        //Verify Club Admin Auth with Apply_Id, User_Id
        authorizationService.getAuthByUserIdAndApplyId(userId, toCreateApplicationFormDTO.getApplyId());

        Optional<ApplicationForm> applicationFormOptional = applicationFormRepository.findById(formId);
        if (applicationFormOptional.isEmpty()) {
            throw new CustomException(ErrorStatus.APPLICATION_FORM_NOT_FOUND);
        }

        ApplicationForm applicationForm = applicationFormOptional.get();

        applicationFormRepository.deleteById(formId);

        return createApplicationForm(toCreateApplicationFormDTO, userId);
    }

    @Transactional
    public ApplicationResponseDTO.ToCreateApplicationDTO createApplication(Long applyId, List<MultipartFile> files, ApplicationRequestDTO.ToApplyClubDTO toApplyClub, Long userId) {

        //Verify it is valid apply
        //In constructions!!!
        checkValidApply(applyId);

        //Check it is duplicated
        Optional<Application> applicationOptional = applicationRepository.findByUserIdAndApplyId(userId, applyId);
        if (applicationOptional.isPresent()) {
            Application application = applicationOptional.get();
            if (!Objects.equals(application.getApplicationStatus(), "TEMP")) {
                throw new CustomException(ErrorStatus.APPLICATION_DUPLICATED);
            }
        }

        if (files != null) {
            //업로드 할 파일 검증 (길이, 확장자 등)
            fileService.validateFiles(files);

            if (files.size() > 5) {
                throw new CustomException(ErrorStatus.FILE_TOO_MANY);
            }

            //uploadFiles
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String fileUrl = objectStorageService.uploadObject(objectStorageServiceConfig.getApplicationDocsDir(), uuid, file);

                ApplicationDocs applicationDocs = ApplicationDocs.builder()
                        .applicationId(applyId)
                        .fileName(fileName)
                        .fileUrl(fileUrl)
                        .build();
                applicationDocsRepository.save(applicationDocs);
            }
        }

        Application application = Application.builder()
                .userId(userId)
                .applyId(applyId)
                .applicationFormId(toApplyClub.getApplicationFormId())
                .applicationBody(toApplyClub.getFormBody())
                .applicationStatus(toApplyClub.getStatus())
                .clubName(toApplyClub.getClubName())
                .build();

        if (Objects.equals(application.getApplicationStatus(), "TEMP")) {
            deleteApplication(applyId, userId);
        }

        Application applicationId = applicationRepository.save(application);

        return ApplicationResponseDTO.ToCreateApplicationDTO.builder()
                .applyId(applicationId.getId())
                .build();
    }

    public void checkValidApply(Long applyId) {

        //send to apply

        if (false) {
            throw new CustomException(ErrorStatus._BAD_REQUEST);
        }

    }

    @Transactional
    public void deleteApplication(Long applyId, Long userId) {

        Optional<Application> applicationOptional = applicationRepository.findByUserIdAndApplyId(userId, applyId);

        //If application not present
        if (applicationOptional.isEmpty()) {
            throw new CustomException(ErrorStatus.APPLICATION_NOT_PRESENT);
        }

        Application application = applicationOptional.get();

        //If application is not owner of user
        if (!userId.equals(application.getUserId())) {
            throw new CustomException(ErrorStatus.APPLICATION_UNAUTHORIZED);
        }

        //If application couldn't be deleted.
        if (Objects.equals(application.getApplicationStatus(), "SAVED")) {
            throw new CustomException(ErrorStatus.APPLICATION_NOT_CHANGEABLE);
        }

        List<ApplicationDocs> applicationDocsList = applicationDocsRepository.findAllByApplicationId(applyId);

        if (!applicationDocsList.isEmpty()) {
            for (ApplicationDocs applicationDocs : applicationDocsList) {
                objectStorageService.deleteObject(applicationDocs.getFileUrl());
                applicationDocsRepository.deleteByApplicationId(applicationDocs.getApplicationId());
            }
        }

        applicationRepository.delete(application);
    }

    @Transactional
    public ApplicationResponseDTO.ToCreateApplicationDTO changeApplication(Long applyId, List<MultipartFile> files, ApplicationRequestDTO.ToApplyClubDTO toApplyClub, Long userId) {
        deleteApplication(applyId, userId);
        return createApplication(applyId, files, toApplyClub, userId);
    }

    @Transactional(readOnly = true)
    public ApplicationResponseDTO.ToGetApplicationHistoryListDTO getApplicationHistoryList(Long userId) {

        List<Application> applicationList = applicationRepository.findAllByUserId(userId);

        List<ApplicationResponseDTO.ToGetApplicationHistoryDTO> applicationHistoryDTOs = applicationList.stream()
                .map(application -> ApplicationResponseDTO.ToGetApplicationHistoryDTO.builder()
                        .applicationId(application.getId())
                        .clubName(application.getClubName())
                        .status(application.getApplicationStatus())
                        .build())
                .collect(Collectors.toList());

        return ApplicationResponseDTO.ToGetApplicationHistoryListDTO.builder()
                .toGetApplicationHistoryDTO(applicationHistoryDTOs)
                .build();

    }
}

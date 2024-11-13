package club.gach_dong.service;

import club.gach_dong.domain.Application;
import club.gach_dong.domain.ApplicationDocs;
import club.gach_dong.domain.ApplicationForm;
import club.gach_dong.domain.ApplicationFormStatus;
import club.gach_dong.dto.request.ApplicationRequestDTO;
import club.gach_dong.dto.response.ApplicationResponseDTO;
import club.gach_dong.exception.ApplicationException;
import club.gach_dong.exception.ApplicationException.ApplicationDuplicatedException;
import club.gach_dong.exception.ApplicationException.ApplicationFormInUseException;
import club.gach_dong.exception.ApplicationException.ApplicationNotChangeableException;
import club.gach_dong.exception.ApplicationException.ApplicationUnauthorizedException;
import club.gach_dong.exception.FileException.FileTooManyException;
import club.gach_dong.gcp.ObjectStorageService;
import club.gach_dong.gcp.ObjectStorageServiceConfig;
import club.gach_dong.repository.ApplicationDocsRepository;
import club.gach_dong.repository.ApplicationFormRepository;
import club.gach_dong.repository.ApplicationRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    public ApplicationResponseDTO.ToCreateApplicationFormDTO createApplicationForm(
            ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO, String userId) {

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
    public ApplicationResponseDTO.ToGetFormInfoAdminDTO getFormInfoAdmin(Long formId, String userId) {

        ApplicationForm applicationForm = applicationFormRepository.findById(formId)
                .orElseThrow(ApplicationException.ApplicationFormNotFoundException::new);

        //Verify Club Admin Auth with Apply_Id, User_Id
        authorizationService.getAuthByUserIdAndApplyId(userId, applicationForm.getApplyId());

        return ApplicationResponseDTO.ToGetFormInfoAdminDTO.builder()
                .formId(applicationForm.getId())
                .formName(applicationForm.getFormName())
                .formBody(applicationForm.getBody())
                .formStatus(String.valueOf(applicationForm.getApplicationFormStatus()))
                .build();
    }

    @Transactional(readOnly = true)
    public ApplicationResponseDTO.ToGetFormInfoUserDTO getFormInfoUser(Long formId, String userId) {

        ApplicationForm applicationForm = applicationFormRepository.findById(formId)
                .orElseThrow(ApplicationException.ApplicationFormNotFoundException::new);

        return ApplicationResponseDTO.ToGetFormInfoUserDTO.builder()
                .formId(applicationForm.getId())
                .formName(applicationForm.getFormName())
                .formBody(applicationForm.getBody())
                .build();
    }

    @Transactional
    public void deleteApplicationForm(Long formId, String userId) {

        ApplicationForm applicationForm = applicationFormRepository.findById(formId)
                .orElseThrow(ApplicationException.ApplicationFormNotFoundException::new);

        //Verify Club Admin Auth with Apply_Id, User_Id
        authorizationService.getAuthByUserIdAndApplyId(userId, applicationForm.getApplyId());

        if (applicationForm.getApplicationFormStatus() == ApplicationFormStatus.IN_USE) {
            throw new ApplicationFormInUseException();
        }

        applicationFormRepository.deleteById(formId);
    }


    @Transactional
    public ApplicationResponseDTO.ToCreateApplicationFormDTO changeApplicationForm(Long formId,
                                                                                   ApplicationRequestDTO.ToCreateApplicationFormDTO toCreateApplicationFormDTO,
                                                                                   String userId) {

        //Verify it is valid apply
        authorizationService.getApplyIsValid(toCreateApplicationFormDTO.getApplyId());

        //Verify Club Admin Auth with Apply_Id, User_Id
        authorizationService.getAuthByUserIdAndApplyId(userId, toCreateApplicationFormDTO.getApplyId());

        ApplicationForm applicationForm = applicationFormRepository.findById(formId)
                .orElseThrow(ApplicationException.ApplicationFormNotFoundException::new);

        applicationFormRepository.deleteById(formId);

        return createApplicationForm(toCreateApplicationFormDTO, userId);
    }

    @Transactional
    public ApplicationResponseDTO.ToCreateApplicationDTO createApplication(Long applyId, List<MultipartFile> files,
                                                                           ApplicationRequestDTO.ToApplyClubDTO toApplyClub,
                                                                           String userId) {

        //Verify it is valid apply
        authorizationService.getApplyIsValid(applyId);

        //Check it is duplicated
        Optional<Application> applicationOptional = applicationRepository.findByUserIdAndApplyId(userId, applyId);
        if (applicationOptional.isPresent()) {
            Application application = applicationOptional.get();

            if (!Objects.equals(application.getApplicationStatus(), "TEMPORARY_SAVED")) {
                throw new ApplicationDuplicatedException();
            }

            deleteApplication(applyId, userId);

        }

        ApplicationForm applicationForm = applicationFormRepository.findById(toApplyClub.getApplicationFormId())
                .orElseThrow(ApplicationException.ApplicationFormNotFoundException::new);

        if (files != null) {
            //업로드 할 파일 검증 (길이, 확장자 등)
            fileService.validateFiles(files);

            if (files.size() > 5) {
                throw new FileTooManyException();
            }

            //uploadFiles
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String fileUrl = objectStorageService.uploadObject(objectStorageServiceConfig.getApplicationDocsDir(),
                        uuid, file);

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
                .submitDate(LocalDateTime.now())
                .build();

        Application applicationId = applicationRepository.save(application);

        return ApplicationResponseDTO.ToCreateApplicationDTO.builder()
                .applyId(applicationId.getId())
                .build();
    }

    @Transactional
    public void deleteApplication(Long applyId, String userId) {

        Application application = applicationRepository.findByUserIdAndApplyId(userId, applyId)
                .orElseThrow(ApplicationException.ApplicationNotFoundException::new);

        //If application is not owner of user
        if (!userId.equals(application.getUserId())) {
            throw new ApplicationUnauthorizedException();
        }

        //If application couldn't be deleted.
        if (Objects.equals(application.getApplicationStatus(), "SAVED")) {
            throw new ApplicationNotChangeableException();
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
    public ApplicationResponseDTO.ToCreateApplicationDTO changeApplication(Long applyId, List<MultipartFile> files,
                                                                           ApplicationRequestDTO.ToApplyClubDTO toApplyClub,
                                                                           String userId) {
        deleteApplication(applyId, userId);
        return createApplication(applyId, files, toApplyClub, userId);
    }

    @Transactional(readOnly = true)
    public ApplicationResponseDTO.ToGetApplicationHistoryListDTO getApplicationHistoryList(String userId) {

        List<Application> applicationList = applicationRepository.findAllByUserId(userId);

        List<ApplicationResponseDTO.ToGetApplicationHistoryDTO> applicationHistoryDTOs = applicationList.stream()
                .map(application -> ApplicationResponseDTO.ToGetApplicationHistoryDTO.builder()
                        .applicationId(application.getId())
                        .clubName(application.getClubName())
                        .status(application.getApplicationStatus())
                        .submitDate(application.getSubmitDate())
//                        .applicationBody(application.getApplicationBody())
                        .build())
                .collect(Collectors.toList());

        return ApplicationResponseDTO.ToGetApplicationHistoryListDTO.builder()
                .toGetApplicationHistoryDTO(applicationHistoryDTOs)
                .build();

    }

    @Transactional
    public void changeApplicationStatus(String userId,
                                        ApplicationRequestDTO.ToChangeApplicationStatus toChangeApplicationStatus) {

        Application application = applicationRepository.findById(toChangeApplicationStatus.getApplicationId())
                .orElseThrow(ApplicationException.ApplicationNotFoundException::new);

        //Verify Club Admin Auth with Apply_Id, User_Id
        authorizationService.getAuthByUserIdAndApplyId(userId, application.getApplyId());

        applicationRepository.updateApplicationStatus(toChangeApplicationStatus.getStatus(), application);
    }

    @Transactional(readOnly = true)
    public ApplicationResponseDTO.ToGetApplicationListAdminDTO getApplicationListAdmin(String userId, Long applyId) {

        //Verify Club Admin Auth with Apply_Id, User_Id
        authorizationService.getAuthByUserIdAndApplyId(userId, applyId);

        List<Application> applicationList = applicationRepository.findAllByApplyIdAndApplicationStatus(applyId,
                "SAVED");

        if (applicationList.isEmpty()) {
            return ApplicationResponseDTO.ToGetApplicationListAdminDTO.builder()
                    .toGetApplicationDTO(null)
                    .build();
        }

//        if (applicationList.isEmpty()) {
//            throw new CustomException(ErrorCode.APPLICATION_NOT_PRESENT);
//        }

        List<ApplicationResponseDTO.ToGetApplicationDTO> toGetApplicationDTOs = applicationList.stream()
                .map(application -> ApplicationResponseDTO.ToGetApplicationDTO.builder()
                        .applicationId(application.getId())
                        .status(application.getApplicationStatus())
                        .submitDate(application.getSubmitDate())
                        .applicationBody(application.getApplicationBody())
                        .build())
                .collect(Collectors.toList());

        return ApplicationResponseDTO.ToGetApplicationListAdminDTO.builder()
                .toGetApplicationDTO(toGetApplicationDTOs)
                .build();
    }

    @Transactional(readOnly = true)
    public ApplicationResponseDTO.ToGetApplicationTempDTO getApplicationTemp(Long recruitmentId, String userId) {

        Application application = applicationRepository.findByApplyIdAndApplicationStatusAndUserId(recruitmentId,
                "TEMPORARY_SAVED", userId).orElse(null);

        if (application == null) {
            return null;
        }

        return ApplicationResponseDTO.ToGetApplicationTempDTO.builder()
                .applicationId(application.getId())
                .applicationBody(application.getApplicationBody())
                .build();
    }
}

package club.gach_dong.service;



import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.google.cloud.storage.*;
import org.springframework.web.multipart.MultipartFile;
import club.gach_dong.dto.response.UserProfileResponse;
import club.gach_dong.entity.User;
import club.gach_dong.exception.ErrorStatus;
import club.gach_dong.repository.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final Storage gcpStorage;
    private final String bucketName;

    private final long MAX_IMAGE_SIZE = 10 * 1024 * 1024;
    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>(Arrays.asList("png", "jpg", "jpeg"));

    private void validateImage(MultipartFile image) {
        String contentType = image.getContentType();
        String originalFilename = image.getOriginalFilename();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("유효하지 않은 이미지 형식입니다. 이미지 파일을 업로드해 주세요.");
        }

        validateImageExtension(originalFilename);

        if (image.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("이미지 크기가 너무 큽니다. 최대 10MB까지 지원합니다.");
        }
    }

    private void validateImageExtension(String fileName) {
        String fileExtension = getFileExtension(fileName);
        if (!ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            throw new IllegalArgumentException("허용되지 않는 이미지 형식입니다. PNG, JPG, JPEG 파일만 업로드 가능합니다.");
        }
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    private String saveImageFile(String userReferenceId, MultipartFile image) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String fileName = userReferenceId + "_" + uuid + "_" + image.getOriginalFilename();
        InputStream inputStream = image.getInputStream();

        BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, fileName))
                .setContentType(image.getContentType())
                .build();
        gcpStorage.create(blobInfo, inputStream);

        return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
    }

    private void deleteOldImage(User user) throws IOException {
        if (user.getProfileImageUrl() != null) {
            String oldFileName = user.getProfileImageUrl().substring(user.getProfileImageUrl().lastIndexOf('/') + 1);
            gcpStorage.delete(BlobId.of(bucketName, oldFileName));
        }
    }

    @Transactional
    public UserProfileResponse saveProfileImage(String userReferenceId, MultipartFile image) {
        validateImage(image);

        try {
            String imageUrl = saveImageFile(userReferenceId, image);

            User user = userRepository.findByUserReferenceId(userReferenceId)
                    .orElseGet(() -> User.of(userReferenceId, null));
            user.setProfileImageUrl(imageUrl);
            userRepository.save(user);

            return UserProfileResponse.from(user);
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패: " + e.getMessage(), e);
        }
    }

    @Transactional
    public UserProfileResponse updateProfileImage(String userReferenceId, MultipartFile image) {
        validateImage(image);

        try {
            User user = userRepository.findByUserReferenceId(userReferenceId)
                    .orElseThrow(() -> new RuntimeException(ErrorStatus.USER_NOT_FOUND.getMessage()));
            deleteOldImage(user);

            String imageUrl = saveImageFile(userReferenceId, image);

            user.setProfileImageUrl(imageUrl);
            userRepository.save(user);

            return UserProfileResponse.from(user);
        } catch (IOException e) {
            throw new RuntimeException("이미지 수정 실패", e);
        }
    }

    @Transactional
    public void deleteProfileImage(String userReferenceId) {
        User user = userRepository.findByUserReferenceId(userReferenceId)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.USER_NOT_FOUND.getMessage()));

        try {
            deleteOldImage(user);
            user.setProfileImageUrl(null);
            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("이미지 삭제 실패", e);
        }
    }

    public String getProfileImage(String userReferenceId) {
        User user = userRepository.findByUserReferenceId(userReferenceId)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.USER_NOT_FOUND.getMessage()));
        return user.getProfileImageUrl();
    }

    public User getOrCreateUser(String userReferenceId) {
        return userRepository.findByUserReferenceId(userReferenceId)
                .orElseGet(() -> User.of(userReferenceId, null));
    }
}

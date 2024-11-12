package club.gach_dong.service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import club.gach_dong.dto.response.UserProfileResponse;
import club.gach_dong.entity.User;
import club.gach_dong.exception.FileException;
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
            throw new FileException.FileFormatNotSupportedException();
        }

        validateImageExtension(originalFilename);

        if (image.getSize() > MAX_IMAGE_SIZE) {
            throw new FileException.FileTooLargeException();
        }
    }

    private void validateImageExtension(String fileName) {
        String fileExtension = getFileExtension(fileName);
        if (!ALLOWED_EXTENSIONS.contains(fileExtension.toLowerCase())) {
            throw new FileException.FileFormatNotSupportedException();
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
            throw new FileException.FileUploadFailedException();
        }
    }

    @Transactional
    public UserProfileResponse updateProfileImage(String userReferenceId, MultipartFile image) {
        validateImage(image);

        try {
            User user = userRepository.findByUserReferenceId(userReferenceId)
                    .orElseThrow(() -> new FileException.FileNotFoundException());
            deleteOldImage(user);

            String imageUrl = saveImageFile(userReferenceId, image);

            user.setProfileImageUrl(imageUrl);
            userRepository.save(user);

            return UserProfileResponse.from(user);
        } catch (IOException e) {
            throw new FileException.FileUploadFailedException();
        }
    }

    @Transactional
    public void deleteProfileImage(String userReferenceId) {
        User user = userRepository.findByUserReferenceId(userReferenceId)
                .orElseThrow(() -> new FileException.FileNotFoundException());

        try {
            deleteOldImage(user);
            user.setProfileImageUrl(null);
            userRepository.save(user);
        } catch (IOException e) {
            throw new FileException.FileDeleteFailedException();
        }
    }

    public String getProfileImage(String userReferenceId) {
        User user = userRepository.findByUserReferenceId(userReferenceId)
                .orElseThrow(() -> new FileException.FileNotFoundException());
        return user.getProfileImageUrl();
    }

    public User getOrCreateUser(String userReferenceId) {
        return userRepository.findByUserReferenceId(userReferenceId)
                .orElseGet(() -> User.of(userReferenceId, null));
    }
}

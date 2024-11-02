package club_gach_dong.service;

import com.amazonaws.services.s3.AmazonS3;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import club_gach_dong.dto.response.UserProfileResponse;
import club_gach_dong.entity.User;
import club_gach_dong.exception.ErrorStatus;
import club_gach_dong.repository.UserRepository;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AmazonS3 amazonS3Client;
    private final String bucketName = "gachdong";
    private final long MAX_IMAGE_SIZE = 5 * 1024 * 1024;

    private void validateImage(MultipartFile image) {
        String contentType = image.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("유효하지 않은 이미지 형식입니다. 이미지 파일을 업로드해 주세요.");
        }

        if (!contentType.equals("image/png") && !contentType.equals("image/jpeg")) {
            throw new IllegalArgumentException("허용되지 않는 이미지 형식입니다. PNG, JPG, JPEG 파일만 업로드 가능합니다.");
        }

        if (image.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalArgumentException("이미지 크기가 너무 큽니다. 최대 5MB까지 지원합니다.");
        }
    }

    private String saveImageFile(String userId, MultipartFile image) throws IOException {
        String fileName = userId + "_" + image.getOriginalFilename();
        InputStream inputStream = image.getInputStream();

        amazonS3Client.putObject(bucketName, fileName, inputStream, null);

        return "https://" + bucketName + ".s3." + amazonS3Client.getRegionName() + ".amazonaws.com/" + fileName;
    }

    private void deleteOldImage(User user) throws IOException {
        if (user.getProfileImageUrl() != null) {
            String oldFileName = user.getProfileImageUrl().substring(user.getProfileImageUrl().lastIndexOf('/') + 1);
            amazonS3Client.deleteObject(bucketName, oldFileName);
        }
    }

    @Transactional
    public UserProfileResponse saveProfileImage(String email, MultipartFile image) {
        validateImage(image);

        try {
            String imageUrl = saveImageFile(email, image);

            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> User.of(email, null));
            user.setProfileImageUrl(imageUrl);
            userRepository.save(user);

            return UserProfileResponse.from(user);
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패: " + e.getMessage(), e);
        }
    }

    @Transactional
    public UserProfileResponse updateProfileImage(String email, MultipartFile image) {
        validateImage(image);

        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException(ErrorStatus.USER_NOT_FOUND.getMessage()));
            deleteOldImage(user);

            String imageUrl = saveImageFile(email, image);

            user.setProfileImageUrl(imageUrl);
            userRepository.save(user);

            return UserProfileResponse.from(user);
        } catch (IOException e) {
            throw new RuntimeException("이미지 수정 실패", e);
        }
    }

    @Transactional
    public void deleteProfileImage(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(ErrorStatus.USER_NOT_FOUND.getMessage()));

        try {
            deleteOldImage(user);
            user.setProfileImageUrl(null);
            userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("이미지 삭제 실패", e);
        }
    }
}
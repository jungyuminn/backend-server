package club.gach_dong.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import club.gach_dong.dto.UserProfileDto;
import club.gach_dong.entity.User;
import club.gach_dong.exception.ErrorStatus;
import club.gach_dong.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final String imageStoragePath = "C:\\images"; // 이미지 저장 경로(수정 필요)
    private final long MAX_IMAGE_SIZE = 5 * 1024 * 1024; // 최대 이미지 크기: 5MB

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
        Path filePath = Paths.get(imageStoragePath, fileName);

        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "http://yourdomain.com/images/" + fileName;
    }

    private void deleteOldImage(User user) throws IOException {
        if (user.getProfileImageUrl() != null) {
            Path oldFilePath = Paths.get(imageStoragePath, user.getProfileImageUrl().substring(user.getProfileImageUrl().lastIndexOf('/') + 1));
            Files.deleteIfExists(oldFilePath);
        }
    }

    @Transactional
    public UserProfileDto saveProfileImage(String email, MultipartFile image) {
        validateImage(image);

        try {
            String imageUrl = saveImageFile(email, image);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException(ErrorStatus.USER_NOT_FOUND.getMessage()));
            user.setProfileImageUrl(imageUrl);
            userRepository.save(user);

            return new UserProfileDto(user.getId(), user.getEmail(), imageUrl);
        } catch (IOException e) {
            throw new RuntimeException("이미지 업로드 실패: " + e.getMessage(), e);
        }
    }

    @Transactional
    public UserProfileDto updateProfileImage(String email, MultipartFile image) {
        validateImage(image);

        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException(ErrorStatus.USER_NOT_FOUND.getMessage()));
            deleteOldImage(user);

            String imageUrl = saveImageFile(email, image);

            user.setProfileImageUrl(imageUrl);
            userRepository.save(user);

            return new UserProfileDto(user.getId(), user.getEmail(), imageUrl);
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

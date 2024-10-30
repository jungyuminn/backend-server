package club.gach_dong.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessStatus {
    IMAGE_UPLOADED("이미지가 성공적으로 업로드되었습니다."),
    IMAGE_UPDATED("이미지가 성공적으로 업데이트되었습니다."),
    IMAGE_DELETED("이미지가 성공적으로 삭제되었습니다.");

    private final String message;
}

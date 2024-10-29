package club.gach_dong.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "contact_info")
@Schema(description = "동아리의 연락처 정보를 나타내는 엔티티")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "연락처 ID", example = "1")
    private Long id;

    @Column(name = "contact_method", nullable = false)
    @Schema(description = "연락 수단 (예: gmail, instagram)", example = "gmail")
    private String contactMethod;

    @Column(name = "contact_value", nullable = false)
    @Schema(description = "연락처 정보 (예: test@gmail.com, @test)", example = "test@gmail.com")
    private String contactValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    private ContactInfo(String contactMethod, String contactValue, Club club) {
        this.contactMethod = contactMethod;
        this.contactValue = contactValue;
    }

    public static ContactInfo of(String contactMethod, String contactValue, Club club) {
        return new ContactInfo(contactMethod, contactValue, club);
    }
}

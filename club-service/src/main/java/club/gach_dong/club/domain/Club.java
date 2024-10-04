package club.gach_dong.club.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Entity(name = "club")
public class Club {

    @Id
    @Column(name = "club_id", length = 26, columnDefinition = "CHAR(26)")
    private String id;

    @Column(name = "club_name", length = 26, columnDefinition = "CHAR(26)")
    private String name;

    @Column(name = "club_category", nullable = false)
    private ClubCategory category;

    @Column(name = "club_short_description", nullable = false)
    private String shortDescription;

    @Column(name = "club_description", nullable = false)
    private String description;

    @Column(name = "club_image_url")
    private String clubImageUrl;

    @Column(name = "club_established_at")
    private LocalDateTime establishedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Club create(
            String name,
            ClubCategory category,
            String shortDescription,
            String description,
            String clubImageUrl
    ) {
        return new Club(
                UUID.randomUUID().toString(),
                name,
                category,
                shortDescription,
                description,
                clubImageUrl,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
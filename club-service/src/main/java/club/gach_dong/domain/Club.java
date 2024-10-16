package club.gach_dong.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Entity(name = "club")
public class Club {

    @Id
    @Column(name = "club_id", length = 36, columnDefinition = "CHAR(36)")
    private String id;

    @Column(name = "club_name", length = 26, columnDefinition = "CHAR(26)")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "club_category", nullable = false)
    private ClubCategory category;

    @Column(name = "club_short_description", nullable = false)
    private String shortDescription;

    @Column(name = "club_description", nullable = false)
    private String description;

    @Column(name = "club_image_url")
    private String clubImageUrl;

    @Column(name = "recruiting_status")
    private boolean recruitingStatus;

    @Column(name = "club_established_at")
    private LocalDateTime establishedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static Club create(
            String name,
            ClubCategory category,
            String shortDescription,
            String description,
            String clubImageUrl,
            boolean recruitingStatus
    ) {
        return new Club(
                UUID.randomUUID().toString(),
                name,
                category,
                shortDescription,
                description,
                clubImageUrl,
                recruitingStatus,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
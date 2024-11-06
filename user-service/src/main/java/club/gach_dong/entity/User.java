package club.gach_dong.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false, unique = true)
    private String userReferenceId;

    private String profileImageUrl;

    private User(String userReferenceId, String profileImageUrl) {
        this.userReferenceId = userReferenceId;
        this.profileImageUrl = profileImageUrl;
    }

    public static User of(String userReferenceId, String profileImageUrl) {
        return new User(userReferenceId, profileImageUrl);
    }
}

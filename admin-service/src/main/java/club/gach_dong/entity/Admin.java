package club.gach_dong.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false, unique = true)
    private String userReferenceId;

    @Column(nullable = false)
    private String clubName;

    public Admin(String userReferenceId, String clubName) {
        this.userReferenceId = userReferenceId;
        this.clubName = clubName;
    }

    public static Admin of(String userReferenceId, String clubName) {
        return new Admin(userReferenceId, clubName);
    }
}

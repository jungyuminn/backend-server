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

    public Admin(String userReferenceId) {
        this.userReferenceId = userReferenceId;
    }

    public static Admin of(String userReferenceId) {
        return new Admin(userReferenceId);
    }
}

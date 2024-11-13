package club.gach_dong.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "auth_admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false, unique = true)
    private String userReferenceId;

    @Column(length = 255, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private Role role;

    private boolean enabled;

    @Column(length = 255)
    private String profileImageUrl;

    private Admin(String userReferenceId, String email, String password, String name, Role role, boolean enabled, String profileImageUrl) {
        this.userReferenceId = userReferenceId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.enabled = enabled;
        this.profileImageUrl = profileImageUrl;
    }

    public static Admin of(String email, String password, String name, Role role) {
        return new Admin(UUID.randomUUID().toString(), email, password, name, role, true, null);
    }
}

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

    @Column(nullable = false, unique = true)
    private UUID userId;

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

    private Admin(UUID userId, String email, String password, String name, Role role, boolean enabled) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.enabled = enabled;
    }

    public static Admin of(String email, String password, String name, Role role) {
        return new Admin(UUID.randomUUID(), email, password, name, role, true);
    }
}

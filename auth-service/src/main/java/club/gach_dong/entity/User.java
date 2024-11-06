package club.gach_dong.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "auth_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID user_reference_id;

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

    private User(UUID user_reference_id, String email, String password, String name, Role role, boolean enabled) {
        this.user_reference_id = user_reference_id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.enabled = enabled;
    }

    public static User of(String email, String password, String name, Role role) {
        return new User(UUID.randomUUID(), email, password, name, role, true);
    }
}

package club.gach_dong.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "auth")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String role;

    private boolean enabled;
}

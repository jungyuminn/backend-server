package club.gach_dong.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "invite_code")
public class InviteCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    private String userReferenceId;

    @Column(nullable = false, unique = true)
    private String inviteCode;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    public InviteCode(String userReferenceId, String inviteCode, LocalDateTime expiryDate) {
        this.userReferenceId = userReferenceId;
        this.inviteCode = inviteCode;
        this.expiryDate = expiryDate;
    }

    public static InviteCode of(String userReferenceId, String inviteCode, LocalDateTime expiryDate) {
        return new InviteCode(userReferenceId, inviteCode, expiryDate);
    }
}

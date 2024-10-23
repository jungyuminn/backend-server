package club.gach_dong.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Builder
@Entity(name = "applicationDocs")
public class ApplicationDocs {
    @Id
    @Column(name = "application_docs_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_id" ,nullable = false)
    private Long applicationId;

    @Column(name = "file_url" ,nullable = false, length = 255)
    private String fileUrl;

    @Column(name = "file_name" ,nullable = false, length = 30)
    private String fileName;
}

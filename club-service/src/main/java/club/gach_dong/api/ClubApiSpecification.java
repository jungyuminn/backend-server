package club.gach_dong.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "동아리 API", description = "동아리 관련 API")
@RestController
@RequestMapping("/api/v1")
@Validated
public interface ClubApiSpecification {}
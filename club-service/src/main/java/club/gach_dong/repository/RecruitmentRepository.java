package club.gach_dong.repository;

import club.gach_dong.domain.Recruitment;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    @EntityGraph(attributePaths = {"club"})
    Optional<Recruitment> findById(Long recruitmentId);
}

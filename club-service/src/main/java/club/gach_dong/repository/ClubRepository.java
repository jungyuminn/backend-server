package club.gach_dong.repository;

import club.gach_dong.domain.Club;
import club.gach_dong.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findAll();

    Optional<Club> findById(Long id);

    @Query("SELECT c FROM Club c JOIN FETCH c.recruitment")
    List<Club> findAllWithRecruitments();
}

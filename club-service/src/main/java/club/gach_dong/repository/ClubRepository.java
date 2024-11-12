package club.gach_dong.repository;

import club.gach_dong.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findAll();

    Optional<Club> findById(Long id);

    @Query("SELECT c FROM Club c JOIN FETCH c.recruitment")
    List<Club> findAllWithRecruitments();
}

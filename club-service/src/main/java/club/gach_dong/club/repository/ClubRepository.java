package club.gach_dong.club.repository;

import club.gach_dong.club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, String> {
    List<Club> findAll();

    Optional<Club> findByName(String name);
}

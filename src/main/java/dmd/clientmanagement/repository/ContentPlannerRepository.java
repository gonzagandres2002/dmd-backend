package dmd.clientmanagement.repository;

import dmd.clientmanagement.entity.ContentPlanner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ContentPlannerRepository extends JpaRepository<ContentPlanner, Long> {
    List<ContentPlanner> findByUserId(Long userId);
}

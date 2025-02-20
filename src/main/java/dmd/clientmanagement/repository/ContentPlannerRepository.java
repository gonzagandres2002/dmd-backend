package dmd.clientmanagement.repository;

import dmd.clientmanagement.entity.ContentPlanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentPlannerRepository extends JpaRepository<ContentPlanner, Long> {
    List<ContentPlanner> findByUserId(Long userId);
}

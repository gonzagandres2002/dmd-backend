package dmd.clientmanagement.repository;

import dmd.clientmanagement.entity.SolutionTracker;
import dmd.clientmanagement.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionTrackerRepository extends JpaRepository<SolutionTracker, Long> {
    List<SolutionTracker> findByUserId(Long userId);
}

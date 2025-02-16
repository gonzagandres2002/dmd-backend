package dmd.clientmanagement.repository;

import dmd.clientmanagement.entity.SolutionTracker;
import dmd.clientmanagement.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolutionTrackerRepository extends JpaRepository<SolutionTracker, Long> {
    List<SolutionTracker> findByUserId(Long userId);
}

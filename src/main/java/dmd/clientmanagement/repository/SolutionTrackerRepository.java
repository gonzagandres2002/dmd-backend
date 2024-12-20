package dmd.clientmanagement.repository;

import dmd.clientmanagement.entity.solutionTracker.SolutionTracker;
import dmd.clientmanagement.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolutionTrackerRepository extends JpaRepository<SolutionTracker, Long> {
    List<SolutionTracker> findByUser(User user);
}

package dmd.clientmanagement.service;

import dmd.clientmanagement.entity.solutionTracker.SolutionStatus;
import dmd.clientmanagement.entity.solutionTracker.SolutionTracker;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.exceptions.UserNotFoundException;
import dmd.clientmanagement.repository.SolutionTrackerRepository;
import dmd.clientmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolutionTrackerService {

    private final SolutionTrackerRepository solutionTrackerRepository;
    private final UserRepository userRepository;

    public SolutionTrackerService(SolutionTrackerRepository solutionTrackerRepository, UserRepository userRepository) {
        this.solutionTrackerRepository = solutionTrackerRepository;
        this.userRepository = userRepository;
    }

    public List<SolutionTracker> getSolutionsByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return solutionTrackerRepository.findByUser(user);
    }

    public SolutionTracker updateSolutionStatus(Long solutionId, String status) {
        SolutionTracker solution = solutionTrackerRepository.findById(solutionId)
                .orElseThrow(() -> new IllegalArgumentException("Solution not found"));
        solution.setStatus(SolutionStatus.valueOf(status.toUpperCase()));
        return solutionTrackerRepository.save(solution);
    }
}

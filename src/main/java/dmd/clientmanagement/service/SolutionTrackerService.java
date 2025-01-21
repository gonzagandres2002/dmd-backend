package dmd.clientmanagement.service;

import dmd.clientmanagement.dto.SolutionTrackerDto;
import dmd.clientmanagement.entity.solutionTracker.SolutionTracker;
import dmd.clientmanagement.entity.user.User;
import dmd.clientmanagement.exceptions.UserNotFoundException;
import dmd.clientmanagement.repository.SolutionTrackerRepository;
import dmd.clientmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public SolutionTracker updateSolution(Long solutionId, Integer progressPercentage) {
        SolutionTracker solution = solutionTrackerRepository.findById(solutionId)
                .orElseThrow(() -> new IllegalArgumentException("Solution not found"));

        if (progressPercentage != null) {
            if (progressPercentage < 0 || progressPercentage > 100) {
                throw new IllegalArgumentException("Progress percentage must be between 0 and 100.");
            }
            solution.setProgressPercentage(progressPercentage);
        }
        return solutionTrackerRepository.save(solution);
    }

    public SolutionTracker createSolution(SolutionTrackerDto solutionTrackerDto) {
        User user = userRepository.findById(solutionTrackerDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(solutionTrackerDto.getUserId()));

        SolutionTracker solution = new SolutionTracker();
        solution.setUser(user);
        solution.setTitle(solutionTrackerDto.getTitle());
        solution.setDescription(solutionTrackerDto.getDescription());
        solution.setProgressPercentage(solutionTrackerDto.getProgressPercentage());
        solution.setStartDate(solutionTrackerDto.getStartDate() != null ?
                solutionTrackerDto.getStartDate() : LocalDate.now());
        solution.setEndDate(solutionTrackerDto.getEndDate());

        return solutionTrackerRepository.save(solution);
    }




}

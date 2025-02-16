package dmd.clientmanagement.service;

import dmd.clientmanagement.dto.SolutionTrackerDto;
import dmd.clientmanagement.entity.SolutionTracker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SolutionTrackerService {

    List<SolutionTrackerDto> getSolutionsByUserId(Long userId);

    SolutionTrackerDto updateSolution(Long solutionId, SolutionTrackerDto solutionTrackerDto);

    SolutionTrackerDto createSolution(SolutionTrackerDto solutionTrackerDto);

    void deleteSolution(Long solutionId);
}

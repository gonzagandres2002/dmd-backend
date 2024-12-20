package dmd.clientmanagement.controllers;

import dmd.clientmanagement.entity.solutionTracker.SolutionTracker;
import dmd.clientmanagement.service.SolutionTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solutions")
public class SolutionTrackerController {

    private final SolutionTrackerService solutionTrackerService;

    public SolutionTrackerController(SolutionTrackerService solutionTrackerService) {
        this.solutionTrackerService = solutionTrackerService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SolutionTracker>> getSolutionsByUser(@PathVariable Long userId) {
        List<SolutionTracker> solutions = solutionTrackerService.getSolutionsByUserId(userId);
        return ResponseEntity.ok(solutions);
    }

    @PutMapping("/{solutionId}/status")
    public ResponseEntity<SolutionTracker> updateSolutionStatus(@PathVariable Long solutionId, @RequestParam String status) {
        SolutionTracker updatedSolution = solutionTrackerService.updateSolutionStatus(solutionId, status);
        return ResponseEntity.ok(updatedSolution);
    }
}

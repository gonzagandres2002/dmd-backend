package dmd.clientmanagement.controllers;

import dmd.clientmanagement.dto.SolutionTrackerDto;
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

    // Get solutions for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SolutionTracker>> getSolutionsByUser(@PathVariable Long userId) {
        List<SolutionTracker> solutions = solutionTrackerService.getSolutionsByUserId(userId);
        return ResponseEntity.ok(solutions);
    }

    // Create a new solution
    @PostMapping
    public ResponseEntity<SolutionTracker> createSolution(@RequestBody SolutionTrackerDto solutionTrackerDto) {
        SolutionTracker newSolution = solutionTrackerService.createSolution(solutionTrackerDto);
        return ResponseEntity.status(201).body(newSolution);
    }

    // Update solution progress or status
    @PatchMapping("/{solutionId}")
    public ResponseEntity<SolutionTracker> updateSolution(@PathVariable Long solutionId,
                                                          @RequestParam(required = false) Integer progressPercentage) {
        SolutionTracker updatedSolution = solutionTrackerService.updateSolution(solutionId, progressPercentage);
        return ResponseEntity.ok(updatedSolution);
    }
}



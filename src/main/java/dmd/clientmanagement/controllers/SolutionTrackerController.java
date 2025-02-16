package dmd.clientmanagement.controllers;

import dmd.clientmanagement.dto.SolutionTrackerDto;
import dmd.clientmanagement.entity.SolutionTracker;
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
    public ResponseEntity<List<SolutionTrackerDto>> getSolutionsByUser(@PathVariable Long userId) {
        List<SolutionTrackerDto> solutions = solutionTrackerService.getSolutionsByUserId(userId);
        return ResponseEntity.ok(solutions);
    }

    // Create a new solution
    @PostMapping
    public ResponseEntity<SolutionTrackerDto> createSolution(@RequestBody SolutionTrackerDto solutionTrackerDto) {
        SolutionTrackerDto newSolution = solutionTrackerService.createSolution(solutionTrackerDto);
        return ResponseEntity.status(201).body(newSolution);
    }

    // Update solution progress or status
    @PatchMapping("/{solutionId}")
    public ResponseEntity<SolutionTrackerDto> updateSolution(
            @PathVariable Long solutionId,
            @RequestBody SolutionTrackerDto solutionTrackerDto) {
        SolutionTrackerDto updatedSolution = solutionTrackerService.updateSolution(solutionId, solutionTrackerDto);
        return ResponseEntity.ok(updatedSolution);
    }

    @DeleteMapping("/{solutionId}")
    public ResponseEntity<Void> deleteSolution(@PathVariable Long solutionId) {
        solutionTrackerService.deleteSolution(solutionId);
        return ResponseEntity.noContent().build();
    }
}



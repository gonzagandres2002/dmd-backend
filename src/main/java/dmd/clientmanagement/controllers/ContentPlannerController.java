package dmd.clientmanagement.controllers;

import dmd.clientmanagement.dto.ContentPlannerDto;
import dmd.clientmanagement.service.ContentPlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content-planner")
@RequiredArgsConstructor
public class ContentPlannerController {
    private final ContentPlannerService contentPlannerService;

    @PostMapping
    public ResponseEntity<ContentPlannerDto> createContentPlan(@RequestBody ContentPlannerDto contentPlannerDto) {
        return ResponseEntity.ok(contentPlannerService.createContentPlan(contentPlannerDto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ContentPlannerDto>> getContentPlansByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(contentPlannerService.getContentPlansByUserId(userId));
    }

    @PatchMapping("/{plannerId}")
    public ResponseEntity<ContentPlannerDto> updateContentPlan(@PathVariable Long plannerId, @RequestBody ContentPlannerDto contentPlannerDto) {
        return ResponseEntity.ok(contentPlannerService.updateContentPlan(plannerId, contentPlannerDto));
    }

    @DeleteMapping("/{plannerId}")
    public ResponseEntity<Void> deleteContentPlan(@PathVariable Long plannerId) {
        contentPlannerService.deleteContentPlan(plannerId);
        return ResponseEntity.noContent().build();
    }
}

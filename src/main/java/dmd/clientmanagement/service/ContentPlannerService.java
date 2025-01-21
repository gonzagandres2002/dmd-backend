package dmd.clientmanagement.service;

import dmd.clientmanagement.dto.ContentPlannerDto;

import java.util.List;

public interface ContentPlannerService {
    ContentPlannerDto createContentPlan(ContentPlannerDto contentPlannerDto);
    List<ContentPlannerDto> getContentPlansByUserId(Long userId);
    void deleteContentPlan(Long id);
}

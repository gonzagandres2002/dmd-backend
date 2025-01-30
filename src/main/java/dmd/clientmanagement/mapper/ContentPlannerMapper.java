package dmd.clientmanagement.mapper;

import dmd.clientmanagement.dto.ContentPlannerDto;
import dmd.clientmanagement.entity.ContentPlanner;
import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class ContentPlannerMapper {
    public ContentPlannerDto toDto(ContentPlanner contentPlanner) {
        return ContentPlannerDto.builder()
                .userId(contentPlanner.getUser().getId())
                .plannerId(contentPlanner.getId())
                .name(contentPlanner.getName())
                .title(contentPlanner.getTitle())
                .description(contentPlanner.getDescription())
                .scheduledDate(contentPlanner.getScheduledDate())
                .platform(contentPlanner.getPlatform())
                .build();
    }

    public ContentPlanner toEntity(ContentPlannerDto contentPlannerDto, User user, ServiceType serviceType) {
        return ContentPlanner.builder()
                .id(contentPlannerDto.getUserId())
                .name("Content Planner")
                .title(contentPlannerDto.getTitle())
                .description(contentPlannerDto.getDescription())
                .scheduledDate(contentPlannerDto.getScheduledDate())
                .platform(contentPlannerDto.getPlatform())
                .user(user)
                .serviceType(serviceType)
                .build();
    }
}
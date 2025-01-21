package dmd.clientmanagement.mapper;

import dmd.clientmanagement.dto.ContentPlannerDto;
import dmd.clientmanagement.entity.ContentPlanner;
import dmd.clientmanagement.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class ContentPlannerMapper {
    public ContentPlannerDto toDto(ContentPlanner contentPlanner) {
        return ContentPlannerDto.builder()
                .userId(contentPlanner.getId())
                .title(contentPlanner.getTitle())
                .description(contentPlanner.getDescription())
                .scheduledDate(contentPlanner.getScheduledDate())
                .platform(contentPlanner.getPlatform())
                .build();
    }

    public ContentPlanner toEntity(ContentPlannerDto contentPlannerDto, User user) {
        return ContentPlanner.builder()
                .id(contentPlannerDto.getUserId())
                .title(contentPlannerDto.getTitle())
                .description(contentPlannerDto.getDescription())
                .scheduledDate(contentPlannerDto.getScheduledDate())
                .platform(contentPlannerDto.getPlatform())
                .user(user)
                .build();
    }
}
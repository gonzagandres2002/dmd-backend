package dmd.clientmanagement.mapper;

import dmd.clientmanagement.dto.SolutionTrackerDto;
import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.entity.SolutionTracker;
import dmd.clientmanagement.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class SolutionTrackerMapper {
    public SolutionTrackerDto toDto(SolutionTracker solutionTracker) {
        return SolutionTrackerDto.builder()
                .userId(solutionTracker.getUser().getId())
                .solutionId(solutionTracker.getId())
                .title(solutionTracker.getTitle())
                .description(solutionTracker.getDescription())
                .progressPercentage(solutionTracker.getProgressPercentage())
                .startDate(solutionTracker.getStartDate())
                .endDate(solutionTracker.getEndDate())
                .build();
    }

    public SolutionTracker toEntity(SolutionTrackerDto solutionTrackerDto, User user, ServiceType serviceType) {
        return SolutionTracker.builder()
                .id(solutionTrackerDto.getSolutionId())
                .title(solutionTrackerDto.getTitle())
                .description(solutionTrackerDto.getDescription())
                .progressPercentage(solutionTrackerDto.getProgressPercentage())
                .startDate(solutionTrackerDto.getStartDate())
                .endDate(solutionTrackerDto.getEndDate())
                .user(user)
                .serviceType(serviceType)
                .build();
    }


}

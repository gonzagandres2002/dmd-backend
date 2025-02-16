package dmd.clientmanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SolutionTrackerDto {
    private Long userId;
    private Long solutionId;
    private String title;
    private String description;
    private Integer progressPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
}

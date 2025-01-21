package dmd.clientmanagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SolutionTrackerDto {
    private Long userId;
    private String title;
    private String description;
    private Integer progressPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
}

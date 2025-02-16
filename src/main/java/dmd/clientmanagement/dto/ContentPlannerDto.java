package dmd.clientmanagement.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContentPlannerDto {
    private Long userId;
    private Long plannerId;
    private String name;
    private String title;
    private String description;
    private LocalDate scheduledDate;
    private String platform;
}

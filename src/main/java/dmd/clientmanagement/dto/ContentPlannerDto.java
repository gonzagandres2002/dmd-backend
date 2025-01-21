package dmd.clientmanagement.dto;

import dmd.clientmanagement.entity.user.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContentPlannerDto {
    private Long userId;
    private String title;
    private String description;
    private LocalDate scheduledDate;
    private String platform;
}

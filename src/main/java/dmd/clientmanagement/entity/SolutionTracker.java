package dmd.clientmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SolutionTracker extends Functionality {
    private int progressPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
}
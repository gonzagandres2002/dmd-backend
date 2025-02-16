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
public class ContentPlanner extends Functionality {

    private LocalDate scheduledDate;

    private String platform; // e.g., "Facebook", "Twitter", etc.
}

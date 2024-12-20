package dmd.clientmanagement.entity.solutionTracker;

import dmd.clientmanagement.entity.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
public class SolutionTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private SolutionStatus status;

    private LocalDate startDate;
    private LocalDate endDate;
}
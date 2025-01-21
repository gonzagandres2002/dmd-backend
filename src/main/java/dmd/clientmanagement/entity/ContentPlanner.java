package dmd.clientmanagement.entity;

import dmd.clientmanagement.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "content_planners")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContentPlanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate scheduledDate;

    @Column(nullable = false)
    private String platform; // e.g., "Facebook", "Twitter", etc.

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

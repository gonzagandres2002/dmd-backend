package dmd.clientmanagement.entity;

import dmd.clientmanagement.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "services")
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "serviceType", fetch = FetchType.LAZY)
    private List<Functionality> functionalities;

    @ManyToMany(mappedBy = "serviceTypes")
    private List<User> users = new ArrayList<>();

}
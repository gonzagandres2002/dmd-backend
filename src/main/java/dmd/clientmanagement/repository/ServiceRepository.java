package dmd.clientmanagement.repository;

import dmd.clientmanagement.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceType, Long> {
    // Add custom queries if necessary
}

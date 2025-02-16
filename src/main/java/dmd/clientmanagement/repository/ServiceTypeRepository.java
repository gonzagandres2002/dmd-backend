package dmd.clientmanagement.repository;

import dmd.clientmanagement.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    boolean existsByName(String serviceName);
    Optional<ServiceType> findByName(String serviceName);
}

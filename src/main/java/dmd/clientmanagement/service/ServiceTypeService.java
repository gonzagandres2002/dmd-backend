package dmd.clientmanagement.service;


import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceTypeService {

    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceTypeService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    // Retrieve all services
    public List<ServiceType> getAllServices() {
        return serviceRepository.findAll();
    }

    // Retrieve a specific service by ID
    public Optional<ServiceType> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    // Add a new service
    public ServiceType addService(ServiceType service) {
        return serviceRepository.save(service);
    }

    // Update an existing service
    public ServiceType updateService(Long id, ServiceType updatedService) {
        return serviceRepository.findById(id)
                .map(service -> {
                    service.setName(updatedService.getName());
                    service.setDescription(updatedService.getDescription());
                    return serviceRepository.save(service);
                })
                .orElseThrow(() -> new RuntimeException("Service not found with id " + id));
    }

    // Delete a service
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}

package dmd.clientmanagement.controller;

import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceTypeService serviceTypeService;

    @Autowired
    public ServiceController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }

    // GET endpoint to fetch all services
    @GetMapping
    public ResponseEntity<List<ServiceType>> getAllServices() {
        List<ServiceType> services = serviceTypeService.getAllServices();
        return ResponseEntity.ok(services);
    }

    // GET endpoint to fetch a service by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceType> getServiceById(@PathVariable Long id) {
        return serviceTypeService.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST endpoint to create a new service
    @PostMapping
    public ResponseEntity<ServiceType> addService(@RequestBody ServiceType service) {
        ServiceType createdService = serviceTypeService.addService(service);
        return ResponseEntity.ok(createdService);
    }

    // PUT endpoint to update an existing service
    @PutMapping("/{id}")
    public ResponseEntity<ServiceType> updateService(@PathVariable Long id, @RequestBody ServiceType updatedService) {
        try {
            ServiceType service = serviceTypeService.updateService(id, updatedService);
            return ResponseEntity.ok(service);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE endpoint to remove a service
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceTypeService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}

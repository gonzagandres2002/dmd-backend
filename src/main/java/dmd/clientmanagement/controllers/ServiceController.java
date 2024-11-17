package dmd.clientmanagement.controllers;

import dmd.clientmanagement.dto.ServiceTypeDto;
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

    @GetMapping
    public ResponseEntity<List<ServiceTypeDto>> getAllServices() {
        List<ServiceTypeDto> services = serviceTypeService.getAllServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceTypeDto> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceTypeService.getServiceById(id));
    }

    @PostMapping
    public ResponseEntity<ServiceTypeDto> addService(@RequestBody ServiceTypeDto service) {
        ServiceTypeDto createdService = serviceTypeService.addService(service);
        return ResponseEntity.ok(createdService);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceTypeDto> updateService(@PathVariable Long id, @RequestBody ServiceTypeDto updatedService) {
        try {
            ServiceTypeDto service = serviceTypeService.updateService(id, updatedService);
            return ResponseEntity.ok(service);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceTypeService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}

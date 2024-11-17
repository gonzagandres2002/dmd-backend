package dmd.clientmanagement.service;

import dmd.clientmanagement.dto.ServiceTypeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ServiceTypeService {

    List<ServiceTypeDto> getAllServices();

    ServiceTypeDto getServiceById(Long id);

    ServiceTypeDto addService(ServiceTypeDto service);

    ServiceTypeDto updateService(Long id, ServiceTypeDto updatedService);

    void deleteService(Long id);

}

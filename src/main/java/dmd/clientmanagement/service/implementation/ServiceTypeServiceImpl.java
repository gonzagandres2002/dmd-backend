package dmd.clientmanagement.service.implementation;

import dmd.clientmanagement.dto.ServiceTypeDto;
import dmd.clientmanagement.entity.ServiceType;
import dmd.clientmanagement.exceptions.ServiceNotFoundException;
import dmd.clientmanagement.mapper.ServiceTypeMapper;
import dmd.clientmanagement.repository.ServiceTypeRepository;
import dmd.clientmanagement.service.ServiceTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ServiceTypeServiceImpl implements ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceTypeMapper serviceTypeMapper;

    public List<ServiceTypeDto> getAllServices() {
        List<ServiceType> services = serviceTypeRepository.findAll();
        return services.stream().map(serviceTypeMapper::toDto).collect(Collectors.toList());
    }

    public ServiceTypeDto getServiceById(Long id) {
        ServiceType service = serviceTypeRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException(id));
        return serviceTypeMapper.toDto(service);
    }

    public ServiceTypeDto addService(ServiceTypeDto service) {
        ServiceType savedService = serviceTypeRepository.save(serviceTypeMapper.toEntity(service));
        return serviceTypeMapper.toDto(savedService);
    }

    public ServiceTypeDto updateService(Long id, ServiceTypeDto updatedService) {
        ServiceType service = serviceTypeRepository.findById(id).orElseThrow(() -> new ServiceNotFoundException(id));
        service.setName(updatedService.getName());
        return serviceTypeMapper.toDto(serviceTypeRepository.save(service));
    }

    public void deleteService(Long id) {
        serviceTypeRepository.deleteById(id);
    }

}

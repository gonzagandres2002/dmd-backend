package dmd.clientmanagement.mapper;

import dmd.clientmanagement.dto.ServiceTypeDto;
import dmd.clientmanagement.entity.ServiceType;
import org.springframework.stereotype.Component;

@Component
public class ServiceTypeMapper {

    public ServiceTypeDto toDto(ServiceType service) {
        return new ServiceTypeDto(
                service.getId(),
                service.getName()
        );
    }

    public ServiceType toEntity(ServiceTypeDto serviceTypeDto) {
        ServiceType service = new ServiceType();
        service.setId(serviceTypeDto.getId());
        service.setName(serviceTypeDto.getName());
        return service;
    }
}

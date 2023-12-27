package ro.bynaus.nohs.mappers;

import org.mapstruct.Mapper;

import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.models.ServiceDTO;

@Mapper
public interface ServiceMapper {
    Service serviceDtoToService(ServiceDTO dto);
    ServiceDTO serviceToServiceDto(Service service);
}

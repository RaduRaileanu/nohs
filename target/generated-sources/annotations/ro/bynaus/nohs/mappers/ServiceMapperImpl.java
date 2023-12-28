package ro.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.models.ServiceDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-28T11:09:43+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public Service serviceDtoToService(ServiceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Service.ServiceBuilder service = Service.builder();

        service.id( dto.getId() );
        service.message( dto.getMessage() );
        service.type( dto.getType() );

        return service.build();
    }

    @Override
    public ServiceDTO serviceToServiceDto(Service service) {
        if ( service == null ) {
            return null;
        }

        ServiceDTO.ServiceDTOBuilder serviceDTO = ServiceDTO.builder();

        serviceDTO.id( service.getId() );
        serviceDTO.message( service.getMessage() );
        serviceDTO.type( service.getType() );

        return serviceDTO.build();
    }
}

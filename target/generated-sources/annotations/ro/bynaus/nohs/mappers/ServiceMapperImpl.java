package ro.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.models.ServiceDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-19T07:22:39+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
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
        service.type( dto.getType() );
        service.message( dto.getMessage() );

        return service.build();
    }

    @Override
    public ServiceDTO serviceToServiceDto(Service service) {
        if ( service == null ) {
            return null;
        }

        ServiceDTO.ServiceDTOBuilder serviceDTO = ServiceDTO.builder();

        serviceDTO.id( service.getId() );
        serviceDTO.type( service.getType() );
        serviceDTO.message( service.getMessage() );

        return serviceDTO.build();
    }
}

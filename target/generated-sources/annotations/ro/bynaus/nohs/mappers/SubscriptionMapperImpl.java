package ro.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.entities.Subscription;
import ro.bynaus.nohs.models.ServiceDTO;
import ro.bynaus.nohs.models.SubscriptionDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-27T18:02:54+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class SubscriptionMapperImpl implements SubscriptionMapper {

    @Override
    public Subscription SubscriptionDtoToSubscription(SubscriptionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Subscription.SubscriptionBuilder subscription = Subscription.builder();

        subscription.ballance( dto.getBallance() );
        subscription.id( dto.getId() );
        subscription.service( serviceDTOToService( dto.getService() ) );
        subscription.trialRequests( dto.getTrialRequests() );

        return subscription.build();
    }

    @Override
    public SubscriptionDTO subcriptionToSubscriptionDto(Subscription subscription) {
        if ( subscription == null ) {
            return null;
        }

        SubscriptionDTO.SubscriptionDTOBuilder subscriptionDTO = SubscriptionDTO.builder();

        subscriptionDTO.ballance( subscription.getBallance() );
        subscriptionDTO.id( subscription.getId() );
        subscriptionDTO.service( serviceToServiceDTO( subscription.getService() ) );
        subscriptionDTO.trialRequests( subscription.getTrialRequests() );

        return subscriptionDTO.build();
    }

    protected Service serviceDTOToService(ServiceDTO serviceDTO) {
        if ( serviceDTO == null ) {
            return null;
        }

        Service.ServiceBuilder service = Service.builder();

        service.id( serviceDTO.getId() );
        service.message( serviceDTO.getMessage() );
        service.type( serviceDTO.getType() );

        return service.build();
    }

    protected ServiceDTO serviceToServiceDTO(Service service) {
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

package ro.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.bynaus.nohs.entities.BillingInfo;
import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.entities.Subscription;
import ro.bynaus.nohs.models.BillingInfoDTO;
import ro.bynaus.nohs.models.OrganisationDTO;
import ro.bynaus.nohs.models.ServiceDTO;
import ro.bynaus.nohs.models.SubscriptionDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-22T08:52:18+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class OrganisationMapperImpl implements OrganisationMapper {

    @Override
    public OrganisationDTO organisationToOrganisationDTO(Organisation organisation) {
        if ( organisation == null ) {
            return null;
        }

        OrganisationDTO.OrganisationDTOBuilder organisationDTO = OrganisationDTO.builder();

        organisationDTO.id( organisation.getId() );
        organisationDTO.name( organisation.getName() );
        organisationDTO.code( organisation.getCode() );
        organisationDTO.subscription( subscriptionToSubscriptionDTO( organisation.getSubscription() ) );
        organisationDTO.billingInfo( billingInfoToBillingInfoDTO( organisation.getBillingInfo() ) );
        organisationDTO.createdAt( organisation.getCreatedAt() );
        organisationDTO.updatedAt( organisation.getUpdatedAt() );
        organisationDTO.deletedAt( organisation.getDeletedAt() );

        return organisationDTO.build();
    }

    @Override
    public Organisation organisationDTOToOrganisation(OrganisationDTO organisationDTO) {
        if ( organisationDTO == null ) {
            return null;
        }

        Organisation.OrganisationBuilder organisation = Organisation.builder();

        organisation.id( organisationDTO.getId() );
        organisation.name( organisationDTO.getName() );
        organisation.code( organisationDTO.getCode() );
        organisation.subscription( subscriptionDTOToSubscription( organisationDTO.getSubscription() ) );
        organisation.billingInfo( billingInfoDTOToBillingInfo( organisationDTO.getBillingInfo() ) );
        organisation.createdAt( organisationDTO.getCreatedAt() );
        organisation.updatedAt( organisationDTO.getUpdatedAt() );
        organisation.deletedAt( organisationDTO.getDeletedAt() );

        return organisation.build();
    }

    protected ServiceDTO serviceToServiceDTO(Service service) {
        if ( service == null ) {
            return null;
        }

        ServiceDTO.ServiceDTOBuilder serviceDTO = ServiceDTO.builder();

        serviceDTO.id( service.getId() );
        serviceDTO.type( service.getType() );
        serviceDTO.message( service.getMessage() );

        return serviceDTO.build();
    }

    protected SubscriptionDTO subscriptionToSubscriptionDTO(Subscription subscription) {
        if ( subscription == null ) {
            return null;
        }

        SubscriptionDTO.SubscriptionDTOBuilder subscriptionDTO = SubscriptionDTO.builder();

        subscriptionDTO.id( subscription.getId() );
        subscriptionDTO.service( serviceToServiceDTO( subscription.getService() ) );
        subscriptionDTO.trialRequests( subscription.getTrialRequests() );
        subscriptionDTO.ballance( subscription.getBallance() );

        return subscriptionDTO.build();
    }

    protected BillingInfoDTO billingInfoToBillingInfoDTO(BillingInfo billingInfo) {
        if ( billingInfo == null ) {
            return null;
        }

        BillingInfoDTO.BillingInfoDTOBuilder billingInfoDTO = BillingInfoDTO.builder();

        billingInfoDTO.id( billingInfo.getId() );
        billingInfoDTO.city( billingInfo.getCity() );
        billingInfoDTO.country( billingInfo.getCountry() );
        billingInfoDTO.street( billingInfo.getStreet() );
        billingInfoDTO.streetNo( billingInfo.getStreetNo() );
        billingInfoDTO.other( billingInfo.getOther() );
        billingInfoDTO.taxNo( billingInfo.getTaxNo() );
        billingInfoDTO.idNumber( billingInfo.getIdNumber() );
        billingInfoDTO.createdAt( billingInfo.getCreatedAt() );
        billingInfoDTO.updatedAt( billingInfo.getUpdatedAt() );
        billingInfoDTO.deletedAt( billingInfo.getDeletedAt() );

        return billingInfoDTO.build();
    }

    protected Service serviceDTOToService(ServiceDTO serviceDTO) {
        if ( serviceDTO == null ) {
            return null;
        }

        Service.ServiceBuilder service = Service.builder();

        service.id( serviceDTO.getId() );
        service.type( serviceDTO.getType() );
        service.message( serviceDTO.getMessage() );

        return service.build();
    }

    protected Subscription subscriptionDTOToSubscription(SubscriptionDTO subscriptionDTO) {
        if ( subscriptionDTO == null ) {
            return null;
        }

        Subscription.SubscriptionBuilder subscription = Subscription.builder();

        subscription.id( subscriptionDTO.getId() );
        subscription.service( serviceDTOToService( subscriptionDTO.getService() ) );
        subscription.trialRequests( subscriptionDTO.getTrialRequests() );
        subscription.ballance( subscriptionDTO.getBallance() );

        return subscription.build();
    }

    protected BillingInfo billingInfoDTOToBillingInfo(BillingInfoDTO billingInfoDTO) {
        if ( billingInfoDTO == null ) {
            return null;
        }

        BillingInfo.BillingInfoBuilder billingInfo = BillingInfo.builder();

        billingInfo.id( billingInfoDTO.getId() );
        billingInfo.city( billingInfoDTO.getCity() );
        billingInfo.country( billingInfoDTO.getCountry() );
        billingInfo.street( billingInfoDTO.getStreet() );
        billingInfo.streetNo( billingInfoDTO.getStreetNo() );
        billingInfo.other( billingInfoDTO.getOther() );
        billingInfo.taxNo( billingInfoDTO.getTaxNo() );
        billingInfo.idNumber( billingInfoDTO.getIdNumber() );
        billingInfo.createdAt( billingInfoDTO.getCreatedAt() );
        billingInfo.updatedAt( billingInfoDTO.getUpdatedAt() );
        billingInfo.deletedAt( billingInfoDTO.getDeletedAt() );

        return billingInfo.build();
    }
}

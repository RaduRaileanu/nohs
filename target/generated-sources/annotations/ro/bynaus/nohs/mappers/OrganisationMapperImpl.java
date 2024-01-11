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
    date = "2024-01-11T07:25:28+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class OrganisationMapperImpl implements OrganisationMapper {

    @Override
    public OrganisationDTO organisationToOrganisationDTO(Organisation organisation) {
        if ( organisation == null ) {
            return null;
        }

        OrganisationDTO.OrganisationDTOBuilder organisationDTO = OrganisationDTO.builder();

        organisationDTO.billingInfo( billingInfoToBillingInfoDTO( organisation.getBillingInfo() ) );
        organisationDTO.code( organisation.getCode() );
        organisationDTO.createdAt( organisation.getCreatedAt() );
        organisationDTO.deletedAt( organisation.getDeletedAt() );
        organisationDTO.id( organisation.getId() );
        organisationDTO.name( organisation.getName() );
        organisationDTO.subscription( subscriptionToSubscriptionDTO( organisation.getSubscription() ) );
        organisationDTO.updatedAt( organisation.getUpdatedAt() );

        return organisationDTO.build();
    }

    @Override
    public Organisation organisationDTOToOrganisation(OrganisationDTO organisationDTO) {
        if ( organisationDTO == null ) {
            return null;
        }

        Organisation.OrganisationBuilder organisation = Organisation.builder();

        organisation.billingInfo( billingInfoDTOToBillingInfo( organisationDTO.getBillingInfo() ) );
        organisation.code( organisationDTO.getCode() );
        organisation.createdAt( organisationDTO.getCreatedAt() );
        organisation.deletedAt( organisationDTO.getDeletedAt() );
        organisation.id( organisationDTO.getId() );
        organisation.name( organisationDTO.getName() );
        organisation.subscription( subscriptionDTOToSubscription( organisationDTO.getSubscription() ) );
        organisation.updatedAt( organisationDTO.getUpdatedAt() );

        return organisation.build();
    }

    protected BillingInfoDTO billingInfoToBillingInfoDTO(BillingInfo billingInfo) {
        if ( billingInfo == null ) {
            return null;
        }

        BillingInfoDTO.BillingInfoDTOBuilder billingInfoDTO = BillingInfoDTO.builder();

        billingInfoDTO.city( billingInfo.getCity() );
        billingInfoDTO.country( billingInfo.getCountry() );
        billingInfoDTO.createdAt( billingInfo.getCreatedAt() );
        billingInfoDTO.deletedAt( billingInfo.getDeletedAt() );
        billingInfoDTO.id( billingInfo.getId() );
        billingInfoDTO.idNumber( billingInfo.getIdNumber() );
        billingInfoDTO.other( billingInfo.getOther() );
        billingInfoDTO.street( billingInfo.getStreet() );
        billingInfoDTO.streetNo( billingInfo.getStreetNo() );
        billingInfoDTO.taxNo( billingInfo.getTaxNo() );
        billingInfoDTO.updatedAt( billingInfo.getUpdatedAt() );

        return billingInfoDTO.build();
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

    protected SubscriptionDTO subscriptionToSubscriptionDTO(Subscription subscription) {
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

    protected BillingInfo billingInfoDTOToBillingInfo(BillingInfoDTO billingInfoDTO) {
        if ( billingInfoDTO == null ) {
            return null;
        }

        BillingInfo.BillingInfoBuilder billingInfo = BillingInfo.builder();

        billingInfo.city( billingInfoDTO.getCity() );
        billingInfo.country( billingInfoDTO.getCountry() );
        billingInfo.createdAt( billingInfoDTO.getCreatedAt() );
        billingInfo.deletedAt( billingInfoDTO.getDeletedAt() );
        billingInfo.id( billingInfoDTO.getId() );
        billingInfo.idNumber( billingInfoDTO.getIdNumber() );
        billingInfo.other( billingInfoDTO.getOther() );
        billingInfo.street( billingInfoDTO.getStreet() );
        billingInfo.streetNo( billingInfoDTO.getStreetNo() );
        billingInfo.taxNo( billingInfoDTO.getTaxNo() );
        billingInfo.updatedAt( billingInfoDTO.getUpdatedAt() );

        return billingInfo.build();
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

    protected Subscription subscriptionDTOToSubscription(SubscriptionDTO subscriptionDTO) {
        if ( subscriptionDTO == null ) {
            return null;
        }

        Subscription.SubscriptionBuilder subscription = Subscription.builder();

        subscription.ballance( subscriptionDTO.getBallance() );
        subscription.id( subscriptionDTO.getId() );
        subscription.service( serviceDTOToService( subscriptionDTO.getService() ) );
        subscription.trialRequests( subscriptionDTO.getTrialRequests() );

        return subscription.build();
    }
}

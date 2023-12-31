package ro.bynaus.nohs.mappers;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.bynaus.nohs.entities.BillingInfo;
import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.entities.Payment;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.entities.Subscription;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.models.BillingInfoDTO;
import ro.bynaus.nohs.models.OrganisationDTO;
import ro.bynaus.nohs.models.PaymentDTO;
import ro.bynaus.nohs.models.ServiceDTO;
import ro.bynaus.nohs.models.SubscriptionDTO;
import ro.bynaus.nohs.models.UserDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-31T06:49:09+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private OrganisationMapper organisationMapper;

    @Override
    public User userDtoToUser(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.billingInfo( billingInfoDTOToBillingInfo( dto.getBillingInfo() ) );
        user.createdAt( dto.getCreatedAt() );
        user.deletedAt( dto.getDeletedAt() );
        user.email( dto.getEmail() );
        user.firstName( dto.getFirstName() );
        user.id( dto.getId() );
        user.lastName( dto.getLastName() );
        user.organisation( organisationDTOToOrganisation1( dto.getOrganisation() ) );
        user.password( dto.getPassword() );
        user.payments( paymentDTOSetToPaymentSet( dto.getPayments() ) );
        user.role( dto.getRole() );
        user.subscription( subscriptionDTOToSubscription( dto.getSubscription() ) );
        user.updatedAt( dto.getUpdatedAt() );

        return user.build();
    }

    @Override
    public UserDTO userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.billingInfo( billingInfoToBillingInfoDTO( user.getBillingInfo() ) );
        userDTO.createdAt( user.getCreatedAt() );
        userDTO.deletedAt( user.getDeletedAt() );
        userDTO.email( user.getEmail() );
        userDTO.firstName( user.getFirstName() );
        userDTO.id( user.getId() );
        userDTO.lastName( user.getLastName() );
        userDTO.organisation( organisationMapper.organisationToOrganisationDTO( user.getOrganisation() ) );
        userDTO.password( user.getPassword() );
        userDTO.payments( paymentSetToPaymentDTOSet( user.getPayments() ) );
        userDTO.role( user.getRole() );
        userDTO.subscription( subscriptionToSubscriptionDTO( user.getSubscription() ) );
        userDTO.updatedAt( user.getUpdatedAt() );

        return userDTO.build();
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

    protected Organisation organisationDTOToOrganisation1(OrganisationDTO organisationDTO) {
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

    protected Set<Payment> paymentDTOSetToPaymentSet(Set<PaymentDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Payment> set1 = new LinkedHashSet<Payment>( StrictMath.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PaymentDTO paymentDTO : set ) {
            set1.add( paymentMapper.paymentDtoToPayment( paymentDTO ) );
        }

        return set1;
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

    protected Set<PaymentDTO> paymentSetToPaymentDTOSet(Set<Payment> set) {
        if ( set == null ) {
            return null;
        }

        Set<PaymentDTO> set1 = new LinkedHashSet<PaymentDTO>( StrictMath.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Payment payment : set ) {
            set1.add( paymentMapper.paymentToPaymentDTO( payment ) );
        }

        return set1;
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
}

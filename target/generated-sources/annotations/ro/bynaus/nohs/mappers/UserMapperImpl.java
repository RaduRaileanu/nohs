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
    date = "2024-01-29T16:38:14+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
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

        user.id( dto.getId() );
        user.firstName( dto.getFirstName() );
        user.lastName( dto.getLastName() );
        user.email( dto.getEmail() );
        user.password( dto.getPassword() );
        user.role( dto.getRole() );
        user.organisation( organisationDTOToOrganisation1( dto.getOrganisation() ) );
        user.subscription( subscriptionDTOToSubscription( dto.getSubscription() ) );
        user.billingInfo( billingInfoDTOToBillingInfo( dto.getBillingInfo() ) );
        user.payments( paymentDTOSetToPaymentSet( dto.getPayments() ) );
        user.createdAt( dto.getCreatedAt() );
        user.updatedAt( dto.getUpdatedAt() );
        user.deletedAt( dto.getDeletedAt() );

        return user.build();
    }

    @Override
    public UserDTO userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( user.getId() );
        userDTO.firstName( user.getFirstName() );
        userDTO.lastName( user.getLastName() );
        userDTO.email( user.getEmail() );
        userDTO.password( user.getPassword() );
        userDTO.role( user.getRole() );
        userDTO.organisation( organisationMapper.organisationToOrganisationDTO( user.getOrganisation() ) );
        userDTO.subscription( subscriptionToSubscriptionDTO( user.getSubscription() ) );
        userDTO.billingInfo( billingInfoToBillingInfoDTO( user.getBillingInfo() ) );
        userDTO.payments( paymentSetToPaymentDTOSet( user.getPayments() ) );
        userDTO.createdAt( user.getCreatedAt() );
        userDTO.updatedAt( user.getUpdatedAt() );
        userDTO.deletedAt( user.getDeletedAt() );

        return userDTO.build();
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

    protected Organisation organisationDTOToOrganisation1(OrganisationDTO organisationDTO) {
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

    protected Set<Payment> paymentDTOSetToPaymentSet(Set<PaymentDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Payment> set1 = new LinkedHashSet<Payment>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PaymentDTO paymentDTO : set ) {
            set1.add( paymentMapper.paymentDtoToPayment( paymentDTO ) );
        }

        return set1;
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

    protected Set<PaymentDTO> paymentSetToPaymentDTOSet(Set<Payment> set) {
        if ( set == null ) {
            return null;
        }

        Set<PaymentDTO> set1 = new LinkedHashSet<PaymentDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Payment payment : set ) {
            set1.add( paymentMapper.paymentToPaymentDTO( payment ) );
        }

        return set1;
    }
}

package ro.bynaus.nohs.mappers;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
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
    date = "2024-01-11T07:25:28+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment paymentDtoToPayment(PaymentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.createdAt( dto.getCreatedAt() );
        payment.id( dto.getId() );
        payment.invoiceNo( dto.getInvoiceNo() );
        payment.sum( dto.getSum() );

        return payment.build();
    }

    @Override
    public PaymentDTO paymentToPaymentDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDTO.PaymentDTOBuilder paymentDTO = PaymentDTO.builder();

        paymentDTO.createdAt( payment.getCreatedAt() );
        paymentDTO.id( payment.getId() );
        paymentDTO.invoiceNo( payment.getInvoiceNo() );
        paymentDTO.sum( payment.getSum() );
        paymentDTO.user( userToUserDTO( payment.getUser() ) );

        return paymentDTO.build();
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

    protected OrganisationDTO organisationToOrganisationDTO(Organisation organisation) {
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

    protected Set<PaymentDTO> paymentSetToPaymentDTOSet(Set<Payment> set) {
        if ( set == null ) {
            return null;
        }

        Set<PaymentDTO> set1 = new LinkedHashSet<PaymentDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Payment payment : set ) {
            set1.add( paymentToPaymentDTO( payment ) );
        }

        return set1;
    }

    protected UserDTO userToUserDTO(User user) {
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
        userDTO.organisation( organisationToOrganisationDTO( user.getOrganisation() ) );
        userDTO.password( user.getPassword() );
        userDTO.payments( paymentSetToPaymentDTOSet( user.getPayments() ) );
        userDTO.role( user.getRole() );
        userDTO.subscription( subscriptionToSubscriptionDTO( user.getSubscription() ) );
        userDTO.updatedAt( user.getUpdatedAt() );

        return userDTO.build();
    }
}

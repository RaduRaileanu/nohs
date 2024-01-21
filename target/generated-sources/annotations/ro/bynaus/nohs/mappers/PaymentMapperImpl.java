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
    date = "2024-01-19T07:22:39+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public Payment paymentDtoToPayment(PaymentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.id( dto.getId() );
        payment.sum( dto.getSum() );
        payment.invoiceNo( dto.getInvoiceNo() );
        payment.createdAt( dto.getCreatedAt() );

        return payment.build();
    }

    @Override
    public PaymentDTO paymentToPaymentDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDTO.PaymentDTOBuilder paymentDTO = PaymentDTO.builder();

        paymentDTO.id( payment.getId() );
        paymentDTO.sum( payment.getSum() );
        paymentDTO.invoiceNo( payment.getInvoiceNo() );
        paymentDTO.user( userToUserDTO( payment.getUser() ) );
        paymentDTO.createdAt( payment.getCreatedAt() );

        return paymentDTO.build();
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

    protected OrganisationDTO organisationToOrganisationDTO(Organisation organisation) {
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

        userDTO.id( user.getId() );
        userDTO.firstName( user.getFirstName() );
        userDTO.lastName( user.getLastName() );
        userDTO.email( user.getEmail() );
        userDTO.password( user.getPassword() );
        userDTO.role( user.getRole() );
        userDTO.organisation( organisationToOrganisationDTO( user.getOrganisation() ) );
        userDTO.subscription( subscriptionToSubscriptionDTO( user.getSubscription() ) );
        userDTO.billingInfo( billingInfoToBillingInfoDTO( user.getBillingInfo() ) );
        userDTO.payments( paymentSetToPaymentDTOSet( user.getPayments() ) );
        userDTO.createdAt( user.getCreatedAt() );
        userDTO.updatedAt( user.getUpdatedAt() );
        userDTO.deletedAt( user.getDeletedAt() );

        return userDTO.build();
    }
}

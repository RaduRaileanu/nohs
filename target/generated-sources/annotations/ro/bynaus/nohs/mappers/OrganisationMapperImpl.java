package ro.bynaus.nohs.mappers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.lang.StrictMath;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.entities.Payment;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.entities.Subscription;
import ro.bynaus.nohs.models.OrganisationDTO;
import ro.bynaus.nohs.models.PaymentDTO;
import ro.bynaus.nohs.models.ServiceDTO;
import ro.bynaus.nohs.models.SubscriptionDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-28T11:09:43+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.36.0.v20231114-0937, environment: Java 17.0.9 (Eclipse Adoptium)"
)
@Component
public class OrganisationMapperImpl implements OrganisationMapper {

    @Override
    public Organisation organisationDtoToOrganisation(OrganisationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Organisation.OrganisationBuilder organisation = Organisation.builder();

        organisation.code( dto.getCode() );
        organisation.createdAt( dto.getCreatedAt() );
        organisation.deletedAt( dto.getDeletedAt() );
        organisation.id( dto.getId() );
        organisation.name( dto.getName() );
        organisation.payments( paymentDTOSetToPaymentSet( dto.getPayments() ) );
        organisation.subscription( subscriptionDTOToSubscription( dto.getSubscription() ) );
        organisation.updatedAt( dto.getUpdatedAt() );

        return organisation.build();
    }

    @Override
    public OrganisationDTO organisationToOrganisationDTO(Organisation organisation) {
        if ( organisation == null ) {
            return null;
        }

        OrganisationDTO.OrganisationDTOBuilder organisationDTO = OrganisationDTO.builder();

        organisationDTO.code( organisation.getCode() );
        organisationDTO.createdAt( organisation.getCreatedAt() );
        organisationDTO.deletedAt( organisation.getDeletedAt() );
        organisationDTO.id( organisation.getId() );
        organisationDTO.name( organisation.getName() );
        organisationDTO.payments( paymentSetToPaymentDTOSet( organisation.getPayments() ) );
        organisationDTO.subscription( subscriptionToSubscriptionDTO( organisation.getSubscription() ) );
        organisationDTO.updatedAt( organisation.getUpdatedAt() );

        return organisationDTO.build();
    }

    protected Payment paymentDTOToPayment(PaymentDTO paymentDTO) {
        if ( paymentDTO == null ) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.createdAt( paymentDTO.getCreatedAt() );
        payment.id( paymentDTO.getId() );
        payment.invoiceNo( paymentDTO.getInvoiceNo() );
        payment.organisation( organisationDtoToOrganisation( paymentDTO.getOrganisation() ) );
        payment.sum( paymentDTO.getSum() );

        return payment.build();
    }

    protected Set<Payment> paymentDTOSetToPaymentSet(Set<PaymentDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Payment> set1 = new LinkedHashSet<Payment>( StrictMath.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PaymentDTO paymentDTO : set ) {
            set1.add( paymentDTOToPayment( paymentDTO ) );
        }

        return set1;
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

    protected PaymentDTO paymentToPaymentDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDTO.PaymentDTOBuilder paymentDTO = PaymentDTO.builder();

        paymentDTO.createdAt( payment.getCreatedAt() );
        paymentDTO.id( payment.getId() );
        paymentDTO.invoiceNo( payment.getInvoiceNo() );
        paymentDTO.organisation( organisationToOrganisationDTO( payment.getOrganisation() ) );
        paymentDTO.sum( payment.getSum() );

        return paymentDTO.build();
    }

    protected Set<PaymentDTO> paymentSetToPaymentDTOSet(Set<Payment> set) {
        if ( set == null ) {
            return null;
        }

        Set<PaymentDTO> set1 = new LinkedHashSet<PaymentDTO>( StrictMath.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Payment payment : set ) {
            set1.add( paymentToPaymentDTO( payment ) );
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

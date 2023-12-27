package ro.bynaus.nohs.mappers;

import java.util.LinkedHashSet;
import java.util.Set;
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
    date = "2023-12-27T18:53:52+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class OrganisationMapperImpl implements OrganisationMapper {

    @Override
    public Organisation organisationDtoToOrganisation(OrganisationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Organisation.OrganisationBuilder organisation = Organisation.builder();

        organisation.id( dto.getId() );
        organisation.name( dto.getName() );
        organisation.code( dto.getCode() );
        organisation.subscription( subscriptionDTOToSubscription( dto.getSubscription() ) );
        organisation.payments( paymentDTOSetToPaymentSet( dto.getPayments() ) );
        organisation.createdAt( dto.getCreatedAt() );
        organisation.updatedAt( dto.getUpdatedAt() );
        organisation.deletedAt( dto.getDeletedAt() );

        return organisation.build();
    }

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
        organisationDTO.payments( paymentSetToPaymentDTOSet( organisation.getPayments() ) );
        organisationDTO.createdAt( organisation.getCreatedAt() );
        organisationDTO.updatedAt( organisation.getUpdatedAt() );
        organisationDTO.deletedAt( organisation.getDeletedAt() );

        return organisationDTO.build();
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

    protected Payment paymentDTOToPayment(PaymentDTO paymentDTO) {
        if ( paymentDTO == null ) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.id( paymentDTO.getId() );
        payment.sum( paymentDTO.getSum() );
        payment.invoiceNo( paymentDTO.getInvoiceNo() );
        payment.organisation( organisationDtoToOrganisation( paymentDTO.getOrganisation() ) );
        payment.createdAt( paymentDTO.getCreatedAt() );

        return payment.build();
    }

    protected Set<Payment> paymentDTOSetToPaymentSet(Set<PaymentDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Payment> set1 = new LinkedHashSet<Payment>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PaymentDTO paymentDTO : set ) {
            set1.add( paymentDTOToPayment( paymentDTO ) );
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

    protected PaymentDTO paymentToPaymentDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentDTO.PaymentDTOBuilder paymentDTO = PaymentDTO.builder();

        paymentDTO.id( payment.getId() );
        paymentDTO.sum( payment.getSum() );
        paymentDTO.invoiceNo( payment.getInvoiceNo() );
        paymentDTO.organisation( organisationToOrganisationDTO( payment.getOrganisation() ) );
        paymentDTO.createdAt( payment.getCreatedAt() );

        return paymentDTO.build();
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
}

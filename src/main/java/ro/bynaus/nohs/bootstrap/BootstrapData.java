package ro.bynaus.nohs.bootstrap;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.entities.Payment;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.entities.Subscription;
import ro.bynaus.nohs.mappers.OrganisationMapper;
import ro.bynaus.nohs.models.OrganisationDTO;
import ro.bynaus.nohs.repositories.OrganisationRepository;
import ro.bynaus.nohs.repositories.PaymentRepository;
import ro.bynaus.nohs.repositories.ServiceRepository;
import ro.bynaus.nohs.repositories.SubscriptionRepository;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner{

    //repositories
    private final ServiceRepository serviceRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final OrganisationRepository organisationRepository;

    //mappers
    private final OrganisationMapper organisationMapper;

    @Override
    public void run(String... args) throws Exception {
        // loadSubscriptions();
        // loadPayments();
        // loadOrganisations();
        long serviceCount = serviceRepository.count();
        System.out.println("Services no: " + serviceCount);
        long paymentsCount = paymentRepository.count();
        System.out.println("Payments no: " + paymentsCount);
        long organisationsCount = paymentRepository.count();
        System.out.println("Organisations no: " + organisationsCount);
        long subscriptionCount = subscriptionRepository.count();
        System.out.println("Subscriptions no: " + subscriptionCount);

        // Organisation organisation = organisationRepository.findFirstByOrderByIdAsc().orElse(null);
        // OrganisationDTO orgDto = organisationMapper.organisationToOrganisationDTO(organisation);
        // System.out.println(orgDto.getSubscription().getBallance());

    }

    private void loadSubscriptions(){
        Service service = serviceRepository.findById(1).orElse(null);
        Subscription subscription = Subscription.builder()
                                                .service(service)
                                                .trialRequests(50)
                                                .ballance(5.0)
                                                .build();
                                                                
        subscriptionRepository.save(subscription);
    }

    private void loadPayments(){
        Payment payment1 = Payment.builder()
                                    .sum(5.00)
                                    .invoiceNo("TST001")
                                    .build();

        paymentRepository.save(payment1);
    }

    private void loadOrganisations(){
        // construct the set of payments for the new organisation
        Payment payment1 = paymentRepository.findFirstByOrderByIdAsc().orElse(null);
        Set<Payment> payments = new HashSet<Payment>();
        payments.add(payment1);

        // get the subscription for the new organisation
        Subscription subscription1 = subscriptionRepository.findFirstByOrderByIdAsc().orElse(null);

        // build the new organisation and save it to the repository
        Organisation organisation1 = Organisation.builder()
                                                    .name("Test Corp.")
                                                    .code("test_corp")
                                                    .payments(payments)
                                                    .subscription(subscription1)
                                                    .build();
        
        organisationRepository.save(organisation1);
    }
}

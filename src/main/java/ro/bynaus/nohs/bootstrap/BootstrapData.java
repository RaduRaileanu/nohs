package ro.bynaus.nohs.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.entities.Subscription;
import ro.bynaus.nohs.repositories.ServiceRepository;
import ro.bynaus.nohs.repositories.SubscriptionRepository;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner{
    private final ServiceRepository serviceRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void run(String... args) throws Exception {
        loadSubscription();
        long serviceCount = serviceRepository.count();
        System.out.println(serviceCount);
        long subscriptionCount = subscriptionRepository.count();
        System.out.println(subscriptionCount);
    }

    private void loadSubscription(){
        Service service = serviceRepository.findById(1).orElse(null);
        if(service == null){
            System.out.println("That's it!");
        }
        Subscription subscription = Subscription.builder()
                                                .service(service)
                                                .trialRequests(50)
                                                .ballance(5.0)
                                                .build();
                                                                
        subscriptionRepository.save(subscription);
    }
}

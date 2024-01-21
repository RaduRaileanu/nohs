package ro.bynaus.nohs.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.entities.Subscription;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.mappers.SubscriptionMapper;
import ro.bynaus.nohs.models.SubscriptionDTO;
import ro.bynaus.nohs.repositories.OrganisationRepository;
import ro.bynaus.nohs.repositories.ServiceRepository;
import ro.bynaus.nohs.repositories.SubscriptionRepository;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.security.UserPrincipal;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final ServiceRepository serviceRepository;
    private final OrganisationRepository organisationRepository;

    @Override
    public SubscriptionDTO getAuthUserSubscription(UserPrincipal principal) {
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        // if the user is associated to a company, return that company's subscription
        if(user.getOrganisation() != null){
            return user.getOrganisation().getSubscription() !=null ? 
                            subscriptionMapper.subcriptionToSubscriptionDto(user.getOrganisation().getSubscription()) : 
                            null;
        }

        // if the user is not associated to a company, get the user's subscription;
        return user.getSubscription() != null ?
                    subscriptionMapper.subcriptionToSubscriptionDto(user.getSubscription()) :
                    null;
    }

    @Override
    public SubscriptionDTO createSubscription(UserPrincipal principal, Integer serviceId) throws Error {
        
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        if(user.getOrganisation() != null){
            if("admin".equals(user.getRole())){
                Organisation userOrg = user.getOrganisation();
                
                Subscription subscription = createNewSubscription(serviceId);

                userOrg.setSubscription(subscription);
                organisationRepository.save(userOrg);

                return subscriptionMapper.subcriptionToSubscriptionDto(subscription);
            }

            else {
                throw new Error("User must be admin to subscribe to a service");
            }
        }

        else {
            Subscription subscription = createNewSubscription(serviceId);

            user.setSubscription(subscription);
            userRepository.save(user);

            return subscriptionMapper.subcriptionToSubscriptionDto(subscription);
        }
    }

    @Override
    public SubscriptionDTO updateSubscription(UserPrincipal principal, Integer serviceId) throws Error {
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        if(user.getOrganisation() != null){
            if("admin".equals(user.getRole())){
                Organisation userOrg = user.getOrganisation();

                Subscription updatedSubscription = updateExistingSubscription(userOrg.getSubscription(), serviceId);

                userOrg.setSubscription(updatedSubscription);

                organisationRepository.save(userOrg);

                return subscriptionMapper.subcriptionToSubscriptionDto(updatedSubscription);
            }

            else {
                throw new Error("User must be admin to update the organisation's subscription");
            }
        }

        else {
            Subscription updatedSubscription = updateExistingSubscription(user.getSubscription(), serviceId);

            user.setSubscription(updatedSubscription);

            userRepository.save(user);

            return subscriptionMapper.subcriptionToSubscriptionDto(updatedSubscription);
        }
    }

    private Subscription createNewSubscription (Integer serviceId){
        ro.bynaus.nohs.entities.Service service = serviceRepository.findById(serviceId).orElse(null);

        Subscription subscription = Subscription.builder()
                                                .ballance(0.0)
                                                .trialRequests(50)
                                                .service(service)
                                                .build();

        Subscription savedSubscription = subscriptionRepository.saveAndFlush(subscription);
        
        return savedSubscription;
    }

    private Subscription updateExistingSubscription(Subscription subscription, Integer serviceId){
        ro.bynaus.nohs.entities.Service service = serviceRepository.findById(serviceId).orElse(null);

        subscription.setService(service);
        Subscription updatedSubscription = subscriptionRepository.saveAndFlush(subscription);

        return updatedSubscription;
    }
}

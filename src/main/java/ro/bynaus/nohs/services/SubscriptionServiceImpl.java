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

    /**
     * Retrieve the subscription details associated with the authenticated user.
     *
     * <p>This method fetches the User entity based on the UserPrincipal,
     * checks if the user is associated with an organisation, and returns the subscription details accordingly.
     * If the user is associated with a company, it returns the company's subscription; otherwise, it returns the user's subscription.
     *
     * @param principal The authenticated user's principal containing user details.
     * @return A SubscriptionDTO containing the subscription details associated with the authenticated user, or null if no subscription is found.
     */
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

    /**
     * Create a new subscription for the authenticated user or their associated organisation.
     *
     * <p>This method fetches the User entity based on the UserPrincipal,
     * checks if the user is associated with an organisation, and creates a new subscription accordingly.
     * If the user is associated with a company and has admin privileges, it creates a subscription for the organisation;
     * otherwise, it creates a subscription for the user.
     *
     * @param principal The authenticated user's principal containing user details.
     * @param serviceId  The ID of the service to subscribe to.
     * @return A SubscriptionDTO containing the details of the newly created subscription.
     * @throws Error If the user does not have admin privileges to subscribe to a service for the organisation.
     */
    @Override
    public SubscriptionDTO createSubscription(UserPrincipal principal, Integer serviceId) throws Error {
        
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        // Check if the user is associated with a company
        if(user.getOrganisation() != null){
            // Check if the user has admin privileges to subscribe to a service for the organisation
            if("admin".equals(user.getRole())){
                Organisation userOrg = user.getOrganisation();
                
                // Create a new subscription for the organisation
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
            // Create a new subscription for the user
            Subscription subscription = createNewSubscription(serviceId);

            user.setSubscription(subscription);
            userRepository.save(user);

            return subscriptionMapper.subcriptionToSubscriptionDto(subscription);
        }
    }

    /**
     * Update the subscription details for the authenticated user or their associated organisation.
     *
     * <p>This method fetches the User entity based on the UserPrincipal,
     * checks if the user is associated with an organisation, and updates the subscription details accordingly.
     * If the user is associated with a company and has admin privileges, it updates the organisation's subscription;
     * otherwise, it updates the user's subscription.
     *
     * @param principal The authenticated user's principal containing user details.
     * @param serviceId  The ID of the service to update the subscription with.
     * @return A SubscriptionDTO containing the details of the updated subscription.
     * @throws Error If the user does not have admin privileges to update the organisation's subscription.
     */
    @Override
    public SubscriptionDTO updateSubscription(UserPrincipal principal, Integer serviceId) throws Error {
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        // Check if the user is associated with a company
        if(user.getOrganisation() != null){
            if("admin".equals(user.getRole())){
                // Check if the user has admin privileges to update the organisation's subscription
                Organisation userOrg = user.getOrganisation();

                // Update the existing subscription for the organisation
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
            // Update the existing subscription for the user
            Subscription updatedSubscription = updateExistingSubscription(user.getSubscription(), serviceId);

            user.setSubscription(updatedSubscription);

            userRepository.save(user);

            return subscriptionMapper.subcriptionToSubscriptionDto(updatedSubscription);
        }
    }

    /**
     * Create a new subscription for the given service.
     *
     * <p>This method creates a new Subscription entity with the specified service and default values,
     * saves it to the database, and returns the saved subscription.
     *
     * @param serviceId The ID of the service for the new subscription.
     * @return The newly created Subscription entity.
     */
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

    /**
     * Update an existing subscription with the specified service.
     *
     * <p>This method updates the service of an existing Subscription entity,
     * saves it to the database, and returns the updated subscription.
     *
     * @param subscription The existing subscription to update.
     * @param serviceId    The ID of the service to update the subscription with.
     * @return The updated Subscription entity.
     */
    private Subscription updateExistingSubscription(Subscription subscription, Integer serviceId){
        ro.bynaus.nohs.entities.Service service = serviceRepository.findById(serviceId).orElse(null);

        subscription.setService(service);
        Subscription updatedSubscription = subscriptionRepository.saveAndFlush(subscription);

        return updatedSubscription;
    }
}

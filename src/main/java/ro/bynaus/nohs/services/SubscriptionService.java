package ro.bynaus.nohs.services;

import ro.bynaus.nohs.models.SubscriptionDTO;
import ro.bynaus.nohs.security.UserPrincipal;

public interface SubscriptionService {
    
    SubscriptionDTO getAuthUserSubscription(UserPrincipal principal);

    SubscriptionDTO createSubscription(UserPrincipal principal, Integer serviceId);
    
    SubscriptionDTO updateSubscription(UserPrincipal principal, Integer serviceId);
}

package ro.bynaus.nohs.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.models.SubscriptionDTO;
import ro.bynaus.nohs.security.UserPrincipal;
import ro.bynaus.nohs.services.SubscriptionService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@Component
@RequiredArgsConstructor
@RestController
public class SubscriptionController {
    
    public static final String SUBSCRIPTION_PATH = "/subscription";

    private final SubscriptionService subscriptionService;

    /**
     * Retrieve the subscription details for the authenticated user.
     *
     * @param principal The authenticated user principal.
     * @return SubscriptionDTO representing the subscription details of the authenticated user.
     */
    @GetMapping(SUBSCRIPTION_PATH)
    public SubscriptionDTO getAuthUserSubscription(@AuthenticationPrincipal UserPrincipal principal) {
        return subscriptionService.getAuthUserSubscription(principal);
    }
    
    /**
     * Create a new subscription for the authenticated user.
     *
     * @param principal The authenticated user principal.
     * @param serviceId The ID of the service to subscribe to.
     * @return ResponseEntity with the created SubscriptionDTO if successful, else returns a forbidden response.
     */
    @PostMapping(SUBSCRIPTION_PATH)
    public ResponseEntity<SubscriptionDTO> createSubscription(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Integer serviceId){
        
        // if the user has the permission to subscribe to a service, create the subscription and return a ok response containing the subscription
        try {
            SubscriptionDTO subscriptionDTO = subscriptionService.createSubscription(principal, serviceId);
        
            return ResponseEntity.created(null).body(subscriptionDTO);    
        } 
        // if the user does not have the permission to subscribe to a service, return a 403 forbidden response
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } 
    }

    /**
     * Update the subscription details for the authenticated user.
     *
     * @param principal The authenticated user principal.
     * @param serviceId The ID of the service to update the subscription for.
     * @return ResponseEntity with the updated SubscriptionDTO if successful, else returns a forbidden response.
     */
    @PutMapping(SUBSCRIPTION_PATH)
    public ResponseEntity<SubscriptionDTO> updateSubscription(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Integer serviceId) {
        try {
            SubscriptionDTO subscriptionDTO = subscriptionService.updateSubscription(principal, serviceId);
        
            return ResponseEntity.ok().body(subscriptionDTO);    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}

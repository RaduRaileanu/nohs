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

    @GetMapping(SUBSCRIPTION_PATH)
    public SubscriptionDTO getAuthUserSubscription(@AuthenticationPrincipal UserPrincipal principal) {
        return subscriptionService.getAuthUserSubscription(principal);
    }
    
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

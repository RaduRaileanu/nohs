package ro.bynaus.nohs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.bynaus.nohs.entities.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    
}

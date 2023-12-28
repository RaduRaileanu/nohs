package ro.bynaus.nohs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.bynaus.nohs.entities.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Optional<Subscription> findFirstByOrderByIdAsc();
}

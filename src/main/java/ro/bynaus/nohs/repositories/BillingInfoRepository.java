package ro.bynaus.nohs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.bynaus.nohs.entities.BillingInfo;

public interface BillingInfoRepository extends JpaRepository<BillingInfo, Integer> {
    Optional<BillingInfo> findFirstByOrderByIdAsc();
}

package ro.bynaus.nohs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.bynaus.nohs.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{
    Optional<Payment> findFirstByOrderByIdAsc();
}

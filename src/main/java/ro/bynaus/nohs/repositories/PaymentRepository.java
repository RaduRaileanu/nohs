package ro.bynaus.nohs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.bynaus.nohs.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer>{
    
}

package ro.bynaus.nohs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.bynaus.nohs.entities.Service;

public interface ServiceRepository extends JpaRepository<Service,Integer>{
    
}

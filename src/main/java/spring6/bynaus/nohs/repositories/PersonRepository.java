package spring6.bynaus.nohs.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import spring6.bynaus.nohs.domain.Person;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    
}

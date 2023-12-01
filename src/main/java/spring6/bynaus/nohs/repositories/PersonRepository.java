package spring6.bynaus.nohs.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring6.bynaus.nohs.domain.Person;
import java.util.List;
import java.util.Optional;


@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    
}

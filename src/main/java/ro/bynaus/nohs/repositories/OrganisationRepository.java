package ro.bynaus.nohs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.bynaus.nohs.entities.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Integer> {
    Optional<Organisation> findFirstByOrderByIdAsc();
}

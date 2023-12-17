package ro.bynaus.nohs.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import ro.bynaus.nohs.domain.Organisation;

public interface OrganisationRepository extends ReactiveCrudRepository<Organisation, Integer> {
    
}

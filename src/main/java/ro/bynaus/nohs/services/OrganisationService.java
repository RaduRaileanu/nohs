package ro.bynaus.nohs.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.bynaus.nohs.model.OrganisationDTO;

public interface OrganisationService {
    Flux<OrganisationDTO> listOrganisations();

    Mono<OrganisationDTO> getOrganisationById(Integer orgId);

    Mono<OrganisationDTO> createOrganisation(OrganisationDTO organisationDTO);

    Mono<OrganisationDTO> updateOrganisation(Integer orgId, OrganisationDTO organisationDTO);

    Mono<OrganisationDTO> patchOrganisation(Integer orgId, OrganisationDTO organisationDTO);

    Mono<Void> deleteOrganisation(Integer orgId);
}

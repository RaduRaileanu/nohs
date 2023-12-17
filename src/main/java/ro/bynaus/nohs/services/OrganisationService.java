package ro.bynaus.nohs.services;

import reactor.core.publisher.Flux;
import ro.bynaus.nohs.model.OrganisationDTO;

public interface OrganisationService {
    Flux<OrganisationDTO> listOrganisations();
}

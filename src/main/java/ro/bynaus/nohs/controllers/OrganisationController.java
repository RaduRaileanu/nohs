package ro.bynaus.nohs.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import ro.bynaus.nohs.model.OrganisationDTO;
import ro.bynaus.nohs.services.OrganisationService;

@RestController
@RequiredArgsConstructor
public class OrganisationController {
    
    private static final String ORG_PATH = "/api/organisations";
    private final OrganisationService organisationService;


    @GetMapping(ORG_PATH)
    Flux<OrganisationDTO> listOrganisations(){
        // return Flux.just(OrganisationDTO.builder().id(1).build(), OrganisationDTO.builder().id(2).build());
        return organisationService.listOrganisations();
    }
}

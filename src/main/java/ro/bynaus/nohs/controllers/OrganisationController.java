package ro.bynaus.nohs.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ro.bynaus.nohs.model.OrganisationDTO;
import ro.bynaus.nohs.services.OrganisationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequiredArgsConstructor
public class OrganisationController {
    
    private final OrganisationService organisationService;

    private static final String ORG_PATH = "/api/organisations";
    private static final String ORG_BY_ID_PATH = ORG_PATH + "/{orgId}";


    @GetMapping(ORG_PATH)
    Flux<OrganisationDTO> listOrganisations(){
        return organisationService.listOrganisations();
    }

    @GetMapping(ORG_BY_ID_PATH)
    Mono<OrganisationDTO> getOrganisationById(@PathVariable("orgId") Integer orgId) {
        return organisationService.getOrganisationById(orgId)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(ORG_PATH)
    Mono<ResponseEntity<Void>> createOrganisation(@RequestBody OrganisationDTO organisationDTO) {
        return organisationService.createOrganisation(organisationDTO)
                .map(savedOrganisation -> ResponseEntity.created(UriComponentsBuilder
                                                                    .fromHttpUrl("http://localhost:8080/" + ORG_PATH + "/" + savedOrganisation.getId())
                                                                    .build().toUri())
                                                                    .build());
    }

    @PutMapping(ORG_BY_ID_PATH)
    Mono<ResponseEntity<Void>> updateOrganisation(@PathVariable("orgId") Integer orgId, @RequestBody OrganisationDTO organisationDTO) {
        return organisationService.updateOrganisation(orgId, organisationDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(updatedOrganisationDTO -> ResponseEntity.ok().build());
    }
    
    @PatchMapping(ORG_BY_ID_PATH)
    Mono<ResponseEntity<Void>> patchOrganisation(@PathVariable("orgId") Integer orgId, @RequestBody OrganisationDTO organisationDTO){
        return organisationService.patchOrganisation(orgId, organisationDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(updatedOrganisationDTO -> ResponseEntity.ok().build());
    }

    @DeleteMapping(ORG_BY_ID_PATH)
    Mono<ResponseEntity<Void>> deleteOrganisation(@PathVariable("orgId") Integer orgId){
        return organisationService.getOrganisationById(orgId)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map(foundOrg -> organisationService.deleteOrganisation(foundOrg.getId()))
            .thenReturn(ResponseEntity.noContent().build());
    }

}

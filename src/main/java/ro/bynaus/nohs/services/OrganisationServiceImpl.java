package ro.bynaus.nohs.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import ro.bynaus.nohs.mappers.OrganisationMapper;
import ro.bynaus.nohs.model.OrganisationDTO;
import ro.bynaus.nohs.repository.OrganisationRepository;

@Service
@RequiredArgsConstructor
public class OrganisationServiceImpl implements OrganisationService{
    
    private final OrganisationRepository organisationRepository;
    private final OrganisationMapper organisationMapper;

    public Flux<OrganisationDTO> listOrganisations(){
        return organisationRepository.findAll().map(organisationMapper::organisationToOrganisationDTO);
    }
}

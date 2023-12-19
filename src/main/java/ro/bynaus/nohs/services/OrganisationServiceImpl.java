package ro.bynaus.nohs.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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

    public Mono<OrganisationDTO> getOrganisationById(Integer orgId){
        return organisationRepository.findById(orgId).map(organisationMapper::organisationToOrganisationDTO);
    }

    public Mono<OrganisationDTO> createOrganisation(OrganisationDTO organisation){
        return organisationRepository.save(organisationMapper.organisationDTOToOrganisation(organisation))
                .map(organisationMapper::organisationToOrganisationDTO);
    }

    public Mono<OrganisationDTO> updateOrganisation(Integer orgId, OrganisationDTO organisationDTO){
        return organisationRepository.findById(orgId)
                        .map(foundOrgDTO -> {
                            foundOrgDTO.setName(organisationDTO.getName());
                            foundOrgDTO.setCode(organisationDTO.getCode());
                            foundOrgDTO.setServiceType(organisationDTO.getServiceType());
                            foundOrgDTO.setTrialRequests(organisationDTO.getTrialRequests());
                            return foundOrgDTO;
                        }).flatMap(organisationRepository::save)
                            .map(organisationMapper::organisationToOrganisationDTO);
    }

    public Mono<OrganisationDTO> patchOrganisation(Integer orgId, OrganisationDTO organisationDTO){
        return organisationRepository.findById(orgId)
                        .map(foundOrgDTO -> {
                            if(StringUtils.hasText(organisationDTO.getName())){
                                foundOrgDTO.setName(organisationDTO.getName());
                            }
                            if(StringUtils.hasText(organisationDTO.getCode())){
                                foundOrgDTO.setCode(organisationDTO.getCode());
                            }
                            if(StringUtils.hasText(organisationDTO.getServiceType())){
                                foundOrgDTO.setServiceType(organisationDTO.getServiceType());
                            }
                            if(organisationDTO.getTrialRequests() != null){
                                foundOrgDTO.setTrialRequests(organisationDTO.getTrialRequests());
                            }

                            return foundOrgDTO;
                        }).flatMap(organisationRepository::save)
                            .map(organisationMapper::organisationToOrganisationDTO);
    }

    public Mono<Void> deleteOrganisation(Integer orgId){
        organisationRepository.deleteById(orgId).subscribe();
        return Mono.empty();
    }
}

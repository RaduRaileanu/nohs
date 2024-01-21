package ro.bynaus.nohs.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.mappers.OrganisationMapper;
import ro.bynaus.nohs.models.OrganisationDTO;
import ro.bynaus.nohs.repositories.OrganisationRepository;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.security.UserPrincipal;

@Service
@RequiredArgsConstructor
public class OrganisationServiceImpl implements OrganisationService {
    
    private final UserRepository userRepository;
    private final OrganisationRepository organisationRepository;
    private final OrganisationMapper organisationMapper;
    
    @Override
    public OrganisationDTO getAuthUserOrganisation(UserPrincipal principal) {
        
        User user = userRepository.findById(principal.getUserId()).orElse(null);
        
        if(user.getOrganisation() != null){
            return organisationMapper.organisationToOrganisationDTO(user.getOrganisation());
        }
        
        return null;
    }

    @Override
    public OrganisationDTO storeOrganisation(UserPrincipal principal, OrganisationDTO organisationDto) {
        
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        Organisation organisation = organisationMapper.organisationDTOToOrganisation(organisationDto);

        Set<User> orgUsers = new HashSet<>();

        orgUsers.add(user);

        organisation.setUsers(orgUsers);

        Organisation savedOrganisation = organisationRepository.saveAndFlush(organisation);

        OrganisationDTO savedOrganisationDTO = organisationMapper.organisationToOrganisationDTO(savedOrganisation);

        return savedOrganisationDTO;
    }

    @Override
    public OrganisationDTO updateOrganisation(UserPrincipal principal, OrganisationDTO organisation) throws Error {
        
        User user = userRepository.findById(principal.getUserId()).orElse(null);
        System.out.println("admin".equals(user.getRole()));

        if (!"admin".equals(user.getRole())){
            throw new Error("The user must be an admin to update organisation");
        }

        OrganisationDTO existingOrganisation = organisationMapper.organisationToOrganisationDTO(user.getOrganisation());

        if(StringUtils.hasText(organisation.getName())){

            existingOrganisation.setName(organisation.getName());
        }

        if(StringUtils.hasText(organisation.getCode())){

            existingOrganisation.setCode(organisation.getCode());
        }

        if(organisation.getBillingInfo() != null){

            existingOrganisation.setBillingInfo(organisation.getBillingInfo());
        }

        Organisation savedOrganisation = organisationRepository.saveAndFlush(organisationMapper.organisationDTOToOrganisation(existingOrganisation));

        return organisationMapper.organisationToOrganisationDTO(savedOrganisation);
    }

    @Override
    public void softDeleteOrganisation(UserPrincipal principal) throws Error {
        
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        if(user.getRole() != "admin"){
            throw new Error("The user must be an admin to delete the organisation");
        }

        if(user.getOrganisation() == null){
            throw new Error("The user does not belong to any organisation");
        }

        Organisation organisation = user.getOrganisation();

        organisation.setDeletedAt(LocalDateTime.now());

        organisationRepository.save(organisation);
    }
    
}

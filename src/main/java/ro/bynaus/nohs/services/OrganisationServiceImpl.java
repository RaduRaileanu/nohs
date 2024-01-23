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
    
    /**
     * Retrieve the organisation associated with the authenticated user.
     *
     * <p>This method fetches the user from the UserRepository based on the provided UserPrincipal.
     * If the user has an associated organisation, it is converted to an OrganisationDTO using the
     * OrganisationMapper and returned. If the user does not belong to any organisation, null is returned.
     *
     * @param principal The authenticated user's principal containing user details.
     * @return An OrganisationDTO representing the organisation associated with the user, or null if none.
     */
    @Override
    public OrganisationDTO getAuthUserOrganisation(UserPrincipal principal) {
        
        User user = userRepository.findById(principal.getUserId()).orElse(null);
        
        if(user.getOrganisation() != null){
            return organisationMapper.organisationToOrganisationDTO(user.getOrganisation());
        }
        
        return null;
    }

    /**
     * Store an organisation associated with the authenticated user.
     *
     * <p>This method creates a new Organisation entity from the provided OrganisationDTO,
     * associates the authenticated user with the organisation, and saves it to the database.
     * The stored organisation is then converted to an OrganisationDTO and returned.
     *
     * @param principal       The authenticated user's principal containing user details.
     * @param organisationDto The OrganisationDTO containing details of the organisation to be stored.
     * @return The OrganisationDTO representing the stored organisation.
     */
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

    /**
     * Update details of the organisation associated with the authenticated user.
     *
     * <p>This method first checks if the authenticated user is an admin. If not, it throws an error.
     * It then retrieves the existing organisation details, updates them based on the provided OrganisationDTO,
     * and saves the changes to the database. The updated organisation is converted to an OrganisationDTO and returned.
     *
     * @param principal   The authenticated user's principal containing user details.
     * @param organisation The OrganisationDTO containing updated details of the organisation.
     * @return The OrganisationDTO representing the updated organisation.
     * @throws Error If the authenticated user is not an admin.
     */
    @Override
    public OrganisationDTO updateOrganisation(UserPrincipal principal, OrganisationDTO organisation) throws Error {
        
        User user = userRepository.findById(principal.getUserId()).orElse(null);

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

    /**
     * Soft delete the organisation associated with the authenticated user.
     *
     * <p>This method checks if the authenticated user is an admin. If not, it throws an error.
     * It then retrieves the user's organisation, marks it as deleted by setting the "deletedAt" timestamp,
     * and saves the changes to the database.
     *
     * @param principal The authenticated user's principal containing user details.
     * @throws Error If the authenticated user is not an admin or does not belong to any organisation.
     */
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

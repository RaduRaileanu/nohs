package ro.bynaus.nohs.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.models.OrganisationDTO;
import ro.bynaus.nohs.security.UserPrincipal;
import ro.bynaus.nohs.services.OrganisationService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Component
@RequiredArgsConstructor
@RestController
public class OrganisationController {
    
    public static final String ORGANISATION_PATH = "/organisation";

    private final OrganisationService organisationService;

    /**
     * Retrieve the organisation details for the authenticated user.
     *
     * @param principal The authenticated user principal.
     * @return ResponseEntity with the OrganisationDTO if found, else returns a not found response.
     */
    @GetMapping(ORGANISATION_PATH)
    public ResponseEntity<OrganisationDTO> getAuthUserOrganisation(@AuthenticationPrincipal UserPrincipal principal) {
        
        OrganisationDTO organisationDTO = organisationService.getAuthUserOrganisation(principal);

        if(organisationDTO != null){
            return ResponseEntity.ok().body(organisationDTO);
        } else {
            return ResponseEntity.notFound().build();
        }    
    }

    /**
     * Store an organisation for the authenticated user.
     *
     * @param principal   The authenticated user principal.
     * @param organisation The OrganisationDTO containing the details of the organisation to be stored.
     * @return ResponseEntity with the created OrganisationDTO.
     */
    @PostMapping(ORGANISATION_PATH)
    public ResponseEntity<OrganisationDTO> storeOrganisation(@AuthenticationPrincipal UserPrincipal principal, @RequestBody OrganisationDTO organisation){
        
        OrganisationDTO savedOrganisation = organisationService.storeOrganisation(principal, organisation);

        return ResponseEntity.created(null).body(savedOrganisation);
    }

    /**
     * Update the details of the authenticated user's organisation.
     *
     * @param principal   The authenticated user principal.
     * @param organisation The OrganisationDTO containing the updated details of the organisation.
     * @return ResponseEntity with the updated OrganisationDTO if successful, else returns a not found or forbidden response.
     */
    @PatchMapping(ORGANISATION_PATH)
    public ResponseEntity<OrganisationDTO> updateOrganisation(@AuthenticationPrincipal UserPrincipal principal, @RequestBody OrganisationDTO organisation){
        
        try {
            OrganisationDTO savedOrganisation = organisationService.updateOrganisation(principal, organisation);

            if(savedOrganisation != null){
                return ResponseEntity.ok().body(savedOrganisation);
            } else {
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    /**
     * Soft delete the organisation associated with the authenticated user.
     *
     * @param principal The authenticated user principal.
     * @return ResponseEntity with no content if successful, else returns a forbidden or bad request response.
     */
    @DeleteMapping(ORGANISATION_PATH)
    public ResponseEntity<Void> deleteOrganisation(@AuthenticationPrincipal UserPrincipal principal){
        try {
            organisationService.softDeleteOrganisation(principal);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            if(e.getMessage() == "The user must be an admin to delete the organisation"){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }
    
}

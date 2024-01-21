package ro.bynaus.nohs.services;

import ro.bynaus.nohs.models.OrganisationDTO;
import ro.bynaus.nohs.security.UserPrincipal;

public interface OrganisationService {
    public OrganisationDTO getAuthUserOrganisation (UserPrincipal principal);

    public OrganisationDTO storeOrganisation (UserPrincipal principal, OrganisationDTO organisation);

    public OrganisationDTO updateOrganisation (UserPrincipal principal, OrganisationDTO organisation);

    public void softDeleteOrganisation (UserPrincipal principal);
}

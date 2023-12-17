package ro.bynaus.nohs.mappers;

import org.mapstruct.Mapper;

import ro.bynaus.nohs.domain.Organisation;
import ro.bynaus.nohs.model.OrganisationDTO;

@Mapper
public interface OrganisationMapper {

    OrganisationDTO organisationToOrganisationDTO(Organisation organisation);
    Organisation organisationDTOToOrganisation(OrganisationDTO organisationDTO);
}

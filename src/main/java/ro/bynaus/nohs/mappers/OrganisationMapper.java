package ro.bynaus.nohs.mappers;

import org.mapstruct.Mapper;

import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.models.OrganisationDTO;

@Mapper
public interface OrganisationMapper {
    Organisation organisationDtoToOrganisation(OrganisationDTO dto);
    OrganisationDTO organisationToOrganisationDTO(Organisation organisation);
}

package ro.bynaus.nohs.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.models.OrganisationDTO;

@Mapper(componentModel = "spring")
public interface OrganisationMapper {
    OrganisationDTO organisationToOrganisationDTO(Organisation organisation);
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "payments", ignore = true)
    Organisation organisationDTOToOrganisation(OrganisationDTO organisationDTO);
}
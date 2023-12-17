package ro.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ro.bynaus.nohs.domain.Organisation;
import ro.bynaus.nohs.model.OrganisationDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-12-17T17:24:03+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class OrganisationMapperImpl implements OrganisationMapper {

    @Override
    public OrganisationDTO organisationToOrganisationDTO(Organisation organisation) {
        if ( organisation == null ) {
            return null;
        }

        OrganisationDTO.OrganisationDTOBuilder organisationDTO = OrganisationDTO.builder();

        organisationDTO.id( organisation.getId() );
        organisationDTO.name( organisation.getName() );
        organisationDTO.code( organisation.getCode() );
        organisationDTO.serviceType( organisation.getServiceType() );
        organisationDTO.trialRequests( organisation.getTrialRequests() );
        organisationDTO.createdAt( organisation.getCreatedAt() );
        organisationDTO.updatedAt( organisation.getUpdatedAt() );

        return organisationDTO.build();
    }

    @Override
    public Organisation organisationDTOToOrganisation(OrganisationDTO organisationDTO) {
        if ( organisationDTO == null ) {
            return null;
        }

        Organisation.OrganisationBuilder organisation = Organisation.builder();

        organisation.id( organisationDTO.getId() );
        organisation.name( organisationDTO.getName() );
        organisation.code( organisationDTO.getCode() );
        organisation.serviceType( organisationDTO.getServiceType() );
        organisation.trialRequests( organisationDTO.getTrialRequests() );
        organisation.createdAt( organisationDTO.getCreatedAt() );
        organisation.updatedAt( organisationDTO.getUpdatedAt() );

        return organisation.build();
    }
}

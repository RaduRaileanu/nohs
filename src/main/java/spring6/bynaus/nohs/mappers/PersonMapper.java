package spring6.bynaus.nohs.mappers;

import org.mapstruct.Mapper;

import spring6.bynaus.nohs.domain.Person;
import spring6.bynaus.nohs.models.PersonDTO;

@Mapper
public interface PersonMapper {
    PersonDTO personToPersonDTO(Person person);
    Person personDTOToPerson(PersonDTO personDTO);
}

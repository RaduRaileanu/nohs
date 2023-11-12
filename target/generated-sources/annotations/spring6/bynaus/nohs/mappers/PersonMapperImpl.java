package spring6.bynaus.nohs.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import spring6.bynaus.nohs.domain.Person;
import spring6.bynaus.nohs.models.PersonDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-12T15:38:19+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDTO PersonToPersonDTO(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDTO.PersonDTOBuilder personDTO = PersonDTO.builder();

        personDTO.id( person.getId() );
        personDTO.firstName( person.getFirstName() );
        personDTO.lastName( person.getLastName() );
        personDTO.address( person.getAddress() );

        return personDTO.build();
    }

    @Override
    public Person PersonDTOToPerson(PersonDTO personDTO) {
        if ( personDTO == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        person.id( personDTO.getId() );
        person.firstName( personDTO.getFirstName() );
        person.lastName( personDTO.getLastName() );
        person.address( personDTO.getAddress() );

        return person.build();
    }
}

package spring6.bynaus.nohs.mappers;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import spring6.bynaus.nohs.domain.Person;
import spring6.bynaus.nohs.domain.Post;
import spring6.bynaus.nohs.models.PersonDTO;
import spring6.bynaus.nohs.models.PostDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-30T21:06:01+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230814-2020, environment: Java 17.0.8.1 (Eclipse Adoptium)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDTO personToPersonDTO(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDTO.PersonDTOBuilder personDTO = PersonDTO.builder();

        personDTO.address( person.getAddress() );
        personDTO.firstName( person.getFirstName() );
        personDTO.id( person.getId() );
        personDTO.lastName( person.getLastName() );
        personDTO.posts( postSetToPostDTOSet( person.getPosts() ) );

        return personDTO.build();
    }

    @Override
    public Person personDTOToPerson(PersonDTO personDTO) {
        if ( personDTO == null ) {
            return null;
        }

        Person.PersonBuilder person = Person.builder();

        person.address( personDTO.getAddress() );
        person.firstName( personDTO.getFirstName() );
        person.id( personDTO.getId() );
        person.lastName( personDTO.getLastName() );

        return person.build();
    }

    protected PostDTO postToPostDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO.PostDTOBuilder postDTO = PostDTO.builder();

        postDTO.id( post.getId() );
        postDTO.isHateSpeech( post.getIsHateSpeech() );
        postDTO.justification( post.getJustification() );
        postDTO.origContent( post.getOrigContent() );
        postDTO.redactedContent( post.getRedactedContent() );

        return postDTO.build();
    }

    protected Set<PostDTO> postSetToPostDTOSet(Set<Post> set) {
        if ( set == null ) {
            return null;
        }

        Set<PostDTO> set1 = new LinkedHashSet<PostDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Post post : set ) {
            set1.add( postToPostDTO( post ) );
        }

        return set1;
    }
}

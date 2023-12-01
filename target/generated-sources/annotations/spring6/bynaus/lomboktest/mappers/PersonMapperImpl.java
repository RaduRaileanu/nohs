package spring6.bynaus.lomboktest.mappers;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import spring6.bynaus.lomboktest.domain.Person;
import spring6.bynaus.lomboktest.domain.Post;
import spring6.bynaus.lomboktest.models.PersonDTO;
import spring6.bynaus.lomboktest.models.PostDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-01T19:36:37+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDTO personToPersonDTO(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDTO.PersonDTOBuilder personDTO = PersonDTO.builder();

        personDTO.id( person.getId() );
        personDTO.firstName( person.getFirstName() );
        personDTO.lastName( person.getLastName() );
        personDTO.address( person.getAddress() );
        personDTO.posts( postSetToPostDTOSet( person.getPosts() ) );

        return personDTO.build();
    }

    @Override
    public Person personDTOToPerson(PersonDTO personDTO) {
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

    protected PostDTO postToPostDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO.PostDTOBuilder postDTO = PostDTO.builder();

        postDTO.id( post.getId() );
        postDTO.origContent( post.getOrigContent() );
        postDTO.author( personToPersonDTO( post.getAuthor() ) );
        postDTO.redactedContent( post.getRedactedContent() );
        postDTO.justification( post.getJustification() );

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

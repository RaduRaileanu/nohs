package spring6.bynaus.nohs.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import spring6.bynaus.nohs.domain.Person;
import spring6.bynaus.nohs.domain.Post;
import spring6.bynaus.nohs.mappers.PersonMapper;
import spring6.bynaus.nohs.mappers.PostMapper;
import spring6.bynaus.nohs.models.PersonDTO;
import spring6.bynaus.nohs.models.PostDTO;
import spring6.bynaus.nohs.repositories.PersonRepository;
import spring6.bynaus.nohs.repositories.PostRepository;

@Service
@Primary
public class ModerateContentJPA implements ModerateContent{
    private PostRepository postRepository;
    private PersonRepository personRepository;
    private PostMapper postMapper;
    private PersonMapper personMapper;

    public ModerateContentJPA(PostRepository postRepository, PostMapper postMapper, PersonRepository personRepository, PersonMapper personMapper){
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.personMapper = personMapper;
        this.personRepository = personRepository;

        // Person author = new Person.builder()
        //                     .firstName("Donna")
        //                     .lastName("Jhones")
        //                     .address("221 B Baker Street")
        //                     .build();

        Post post1 = Post.builder()
                        // .id(UUID.randomUUID())
                        .origContent("I like Spring. I hate these stupid Romanians")
                        // .author(new Person())
                        .isHateSpeech(true)
                        .redactedContent("I like SPring. ****")
                        .justification("Saying that you hate all people belonging to a nation is a form o bigotism not acceptable unde freedom of speech principles")
                        .build();

        Post post2 =Post.builder()
                    .id(UUID.randomUUID())
                    .origContent("I like Spring. I hate filthy stupid Slavs")
                    // .author(new Person())
                    .isHateSpeech(true)
                    .redactedContent("I like SPring. ****")
                    .justification("Saying that you hate all people belonging to a group is a form o bigotism not acceptable unde freedom of speech principles")
                    .build();

        this.postRepository.save(post1);
        this.postRepository.save(post2);
    }

    @Override
    public Optional<PostDTO> getModeratedPost(UUID id) {
        return Optional.ofNullable(postMapper.postToPostDTO(postRepository.findById(id).orElse(null)));
        // throw new UnsupportedOperationException("Unimplemented method 'getModeratedPost'");
    }

    @Override
    public List<PostDTO> getModeratedPosts(){
        return postRepository.findAll()
                .stream()
                .map(postMapper::postToPostDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO savePost(PostDTO postDTO){

        return postMapper.postToPostDTO(postRepository.save(postMapper.postDTOToPost(postDTO)));
        
    }
}

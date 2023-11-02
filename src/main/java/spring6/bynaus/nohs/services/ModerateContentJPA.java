package spring6.bynaus.lomboktest.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import spring6.bynaus.nohs.domain.Post;
import spring6.bynaus.nohs.mappers.PostMapper;
import spring6.bynaus.nohs.models.PostDTO;
import spring6.bynaus.nohs.repositories.PostRepository;

@Service
@Primary
@AllArgsConstructor
public class ModerateContentJPA implements ModerateContent{
    private PostRepository postRepository;
    private PostMapper postMapper;

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
    public PostDTO savePost(PostDTO post){
        return post;
        
    }
}

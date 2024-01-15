package ro.bynaus.nohs.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.mappers.PostMapper;
import ro.bynaus.nohs.models.PostDTO;
import ro.bynaus.nohs.repositories.PostRepository;

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {
    
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public Set<PostDTO> getPosts(User user){
        return postRepository.findByUser(user).stream()
                    .map(postMapper::postToPostDto)
                    .collect(Collectors.toSet());
    }
}

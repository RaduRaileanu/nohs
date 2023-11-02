package spring6.bynaus.nohs.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import spring6.bynaus.nohs.models.PostDTO;

public interface ModerateContent {
    List<PostDTO> getModeratedPosts();
    Optional<PostDTO> getModeratedPost(UUID id);
    PostDTO savePost(PostDTO post);
}

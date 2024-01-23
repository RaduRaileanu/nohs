package ro.bynaus.nohs.controllers;

import java.util.Set;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.models.PostDTO;
import ro.bynaus.nohs.models.PostEvaluationDTO;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.security.UserPrincipal;
import ro.bynaus.nohs.services.PostsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
public class PostsController {
    
    private final PostsService postsService;
    private final UserRepository userRepository;

    /**
     * Retrieve all posts for the authenticated user.
     *
     * @param principal The authenticated user principal.
     * @return Set of PostDTO representing the posts associated with the user.
     */
    @GetMapping("/api/v1/posts")
    public Set<PostDTO> getPosts(@AuthenticationPrincipal UserPrincipal principal){
        User user = userRepository.findById(principal.getUserId()).orElse(null);
        return postsService.getPosts(user);
    }

    /**
     * Check if a post contains hate speech.
     *
     * @param principal The authenticated user principal.
     * @param origPost   The original post content to be evaluated.
     * @return ResponseEntity with the PostEvaluationDTO containing the evaluation results, or a forbidden response if an error occurs.
     */
    @PostMapping("/api/v1/post")
    public ResponseEntity<PostEvaluationDTO> checkPost(@AuthenticationPrincipal UserPrincipal principal, @RequestBody String origPost) {
        try {
            PostEvaluationDTO checkedPost = postsService.checkPost(principal, origPost);

            return ResponseEntity.created(null).body(checkedPost);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
    
}

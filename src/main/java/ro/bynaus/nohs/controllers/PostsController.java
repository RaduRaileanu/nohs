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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
public class PostsController {
    
    private final PostsService postsService;
    private final UserRepository userRepository;

    @GetMapping("/api/v1/posts")
    public Set<PostDTO> getPosts(@AuthenticationPrincipal UserPrincipal principal){
        User user = userRepository.findById(principal.getUserId()).orElse(null);
        return postsService.getPosts(user);
    }

    @PostMapping("/api/v1/post")
    public ResponseEntity<PostEvaluationDTO> checkPost(@AuthenticationPrincipal UserPrincipal principal, @RequestBody String origPost) {
        try {
            PostEvaluationDTO checkedPost = postsService.checkPost(principal, origPost);

            return ResponseEntity.created(null).body(checkedPost);
        } catch (Exception e) {

            throw e;
            // return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
    
}

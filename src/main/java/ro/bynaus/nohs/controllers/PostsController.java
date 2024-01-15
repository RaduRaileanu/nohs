package ro.bynaus.nohs.controllers;

import java.util.Set;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.models.PostDTO;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.security.UserPrincipal;
import ro.bynaus.nohs.services.PostsService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
}

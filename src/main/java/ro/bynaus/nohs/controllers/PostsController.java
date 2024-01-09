package ro.bynaus.nohs.controllers;

import java.util.Set;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.models.PostDTO;
import ro.bynaus.nohs.services.PostsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
public class PostsController {
    
    private final PostsService postsService;

    @GetMapping("/api/v1/posts")
    public Set<PostDTO> getPosts(){
        return postsService.getPosts();
    }
}

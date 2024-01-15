package ro.bynaus.nohs.services;

import java.util.Set;

import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.models.PostDTO;

public interface PostsService {
    Set<PostDTO> getPosts(User user);
}

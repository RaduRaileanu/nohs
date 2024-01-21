package ro.bynaus.nohs.services;

import java.util.Set;

import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.models.PostDTO;
import ro.bynaus.nohs.security.UserPrincipal;

public interface PostsService {
    Set<PostDTO> getPosts(User user);

    PostDTO checkPost(UserPrincipal principal, String origPost);
}

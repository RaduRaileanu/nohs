package ro.bynaus.nohs.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ro.bynaus.nohs.models.PostDTO;
import ro.bynaus.nohs.models.PostEvaluationDTO;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.security.UserPrincipal;
import ro.bynaus.nohs.services.PostsService;
import ro.bynaus.nohs.repositories.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PostsControllerTest {

    @Mock
    private PostsService postsService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostsController postsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPosts_shouldReturnPostDTOSet() {
        // Arrange
        UserPrincipal userPrincipal = UserPrincipal.builder()
                                            .userId(1)
                                            .email("test@example.com")
                                            .password("USER")  // Replace "USER" with the actual password
                                            .authorities(Collections.emptyList())  // You might need to provide authorities here
                                            .build();
        User user = new User();
        user.setId(1);
        Set<PostDTO> expectedPostDTOs = new HashSet<>();
        when(userRepository.findById(userPrincipal.getUserId())).thenReturn(java.util.Optional.of(user));
        when(postsService.getPosts(user)).thenReturn(expectedPostDTOs);

        // Act
        Set<PostDTO> result = postsController.getPosts(userPrincipal);

        // Assert
        assertEquals(expectedPostDTOs, result);
        verify(userRepository, times(1)).findById(userPrincipal.getUserId());
        verify(postsService, times(1)).getPosts(user);
    }

    @Test
    void checkPost_shouldReturnPostEvaluationDTO() {
        // Arrange
        UserPrincipal userPrincipal = UserPrincipal.builder()
                                            .userId(1)
                                            .email("test@example.com")
                                            .password("USER")  // Replace "USER" with the actual password
                                            .authorities(Collections.emptyList())  // You might need to provide authorities here
                                            .build();
        String origPost = "This is a test post.";
        PostEvaluationDTO expectedPostEvaluationDTO = PostEvaluationDTO.builder().build();
        when(postsService.checkPost(userPrincipal, origPost)).thenReturn(expectedPostEvaluationDTO);

        // Act
        ResponseEntity<PostEvaluationDTO> result = postsController.checkPost(userPrincipal, origPost);

        // Assert
        assertEquals(expectedPostEvaluationDTO, result.getBody());
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(postsService, times(1)).checkPost(userPrincipal, origPost);
    }

    @Test
    void checkPost_shouldReturnForbiddenResponse_whenExceptionOccurs() {
        // Arrange
        UserPrincipal userPrincipal = UserPrincipal.builder()
                                            .userId(1)
                                            .email("test@example.com")
                                            .password("USER")  // Replace "USER" with the actual password
                                            .authorities(Collections.emptyList())  // You might need to provide authorities here
                                            .build();
        String origPost = "This is a test post.";
        when(postsService.checkPost(userPrincipal, origPost)).thenThrow(new RuntimeException("Test exception"));

        // Act
        ResponseEntity<PostEvaluationDTO> result = postsController.checkPost(userPrincipal, origPost);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
        assertEquals(null, result.getBody());
        verify(postsService, times(1)).checkPost(userPrincipal, origPost);
    }
}
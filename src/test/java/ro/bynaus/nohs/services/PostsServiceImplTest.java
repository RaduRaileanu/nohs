package ro.bynaus.nohs.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.entities.Post;
import ro.bynaus.nohs.entities.Subscription;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.mappers.PostMapper;
import ro.bynaus.nohs.models.CheckPostInfo;
import ro.bynaus.nohs.models.PostDTO;
import ro.bynaus.nohs.models.PostEvaluationDTO;
import ro.bynaus.nohs.repositories.OrganisationRepository;
import ro.bynaus.nohs.repositories.PostRepository;
import ro.bynaus.nohs.repositories.SubscriptionRepository;
import ro.bynaus.nohs.repositories.UserRepository;
import ro.bynaus.nohs.security.UserPrincipal;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PostsServiceImplTest {

    private PostsServiceImpl postsService;
    private PostRepository postRepository;
    private SubscriptionRepository subscriptionRepository;
    private OrganisationRepository organisationRepository;
    private UserRepository userRepository;
    private CheckPostService checkPostService;
    private PostMapper postMapper;
    
    @BeforeEach
    public void setUp() {
        postRepository = mock(PostRepository.class);
        subscriptionRepository = mock(SubscriptionRepository.class);
        organisationRepository = mock(OrganisationRepository.class);
        userRepository = mock(UserRepository.class);
        checkPostService = mock(CheckPostService.class);
        postsService = new PostsServiceImpl(postRepository, null, userRepository, checkPostService, subscriptionRepository, organisationRepository);
        postMapper = mock(PostMapper.class);
    }

    @Test
    public void testGetPosts() {
        User user = new User();
        user.setId(1);
        Post post1 = new Post();
        post1.setId(1);
        post1.setUser(user);
        Post post2 = new Post();
        post2.setId(2);
        post2.setUser(user);

        when(postRepository.findByUser(user)).thenReturn(List.of(post1, post2));

        Set<PostDTO> result = postsService.getPosts(user);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testCheckPost() throws Error {
        UserPrincipal principal = UserPrincipal.builder()
                .userId(1)
                .email("user@example.com")
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
        User user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        Subscription subscription = new Subscription();
        subscription.setId(1);
        subscription.setTrialRequests(5);
        subscription.setBallance(50.0);
        user.setSubscription(subscription);

        when(userRepository.findById(principal.getUserId())).thenReturn(Optional.of(user));

        ro.bynaus.nohs.entities.Service service = new ro.bynaus.nohs.entities.Service();
        service.setId(1);
        Subscription orgSubscription = new Subscription();
        orgSubscription.setId(2);
        orgSubscription.setTrialRequests(0);
        orgSubscription.setBallance(30.0);
        orgSubscription.setService(service);
        Organisation organisation = new Organisation();
        organisation.setId(1);
        organisation.setSubscription(orgSubscription);
        user.setOrganisation(organisation);

        when(checkPostService.runPostCheck(any(), any())).thenReturn(getMockCheckPostInfo());

        Post savedPost = new Post();
        savedPost.setId(1);
        PostDTO savedPostDTO = new PostDTO();
        savedPostDTO.setId(1);
        when(postRepository.save(any())).thenReturn(savedPost);

        Subscription updatedSubscription = new Subscription();
        updatedSubscription.setId(1);
        updatedSubscription.setTrialRequests(4);
        updatedSubscription.setBallance(30.0);
        when(subscriptionRepository.save(any())).thenReturn(updatedSubscription);
        when(postMapper.postDtoToPost(any())).thenReturn(savedPost);

        PostEvaluationDTO result = postsService.checkPost(principal, "Test post");

        assertNotNull(result);
        assertEquals(false, result.getHateSpeech());
        assertEquals("Test post", result.getOriginalContent());
    }

    private CheckPostInfo getMockCheckPostInfo() {
        PostDTO postDto = PostDTO.builder()
                .originalContent("Test post")
                .redactedContent("Test post")
                .hateSpeech(false)
                .build();
        return CheckPostInfo.builder()
                .cost(5.0)
                .postDTO(postDto)
                .build();
    }
}
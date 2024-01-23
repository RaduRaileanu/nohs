package ro.bynaus.nohs.services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {
    
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final CheckPostService checkPostService;
    private final SubscriptionRepository subscriptionRepository;
    private final OrganisationRepository organisationRepository;

    /**
     * Retrieve a set of PostDTOs associated with the given user.
     *
     * <p>This method fetches posts from the PostRepository based on the provided User,
     * maps each post to a PostDTO using the PostMapper, and collects them into a Set.
     *
     * @param user The user for whom to retrieve posts.
     * @return A Set of PostDTOs associated with the given user.
     */
    public Set<PostDTO> getPosts(User user){
        return postRepository.findByUser(user).stream()
                    .map(postMapper::postToPostDto)
                    .collect(Collectors.toSet());
    }

    /**
     * Check a post for hate speech and update user/organisation subscription accordingly.
     *
     * <p>This method checks if the user or their organisation has a valid subscription to perform a post check.
     * It then retrieves the subscription details, checks the post for hate speech using the CheckPostService,
     * updates the subscription balance, and saves the results to the database.
     * The post information is then converted to a PostEvaluationDTO and returned.
     *
     * @param principal The authenticated user's principal containing user details.
     * @param origPost  The original content of the post to be checked for hate speech.
     * @return A PostEvaluationDTO containing the results of the post check, including hate speech status,
     *         original and redacted content and justification
     * @throws Error If the user or their organisation does not have a valid subscription or if there are
     *               insufficient funds to finance the post check.
     */
    @Override
    public PostEvaluationDTO checkPost(UserPrincipal principal, String origPost) throws Error {
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        // Check if the user or their organisation has a valid subscription
        if(!(user.getSubscription() != null || (user.getOrganisation() != null && user.getOrganisation().getSubscription() != null))){
            throw new Error("The user or their company must have a subscription in order to check a post for hate speech");
        }

        // retrieve the user's subscription or, if the user is part of an organisation, the user's organisation's subscription
        Subscription subscription = user.getSubscription() != null ? user.getSubscription() : 
                                                                        user.getOrganisation().getSubscription();

        // Check if there are sufficient funds to finance the post check or if the user is still in their trial period
        if(subscription.getTrialRequests() <= 0 || subscription.getBallance() <= 0){
            throw new Error("There aren't any funds to finance user request.");
        }

        // Determine the service associated with the subscription
        ro.bynaus.nohs.entities.Service service = user.getOrganisation() != null ? 
                                                    user.getOrganisation().getSubscription().getService() :
                                                    user.getSubscription().getService();

        // Run the post check using the CheckPostService
        CheckPostInfo checkPostInfo = checkPostService.runPostCheck(service, origPost);

        // Create a Post entity from the CheckPostInfo
        Post post = postMapper.postDtoToPost(checkPostInfo.getPostDTO());

        // Update subscription details based on the costs of the post check
        if(subscription.getTrialRequests() > 0){
            subscription.setTrialRequests(subscription.getTrialRequests() - 1);
        }
        else {
            subscription.setBallance(subscription.getBallance() - checkPostInfo.getCost());
        }

        // Save the updated subscription details
        Subscription savedSubscription = subscriptionRepository.save(subscription);

        // Update user or organisation details based on who the owner of the subscription is
        if(user.getOrganisation() != null){
            Organisation organisation = user.getOrganisation();
            organisation.setSubscription(savedSubscription);
            Organisation updatedOrganisation = organisationRepository.save(organisation);
            post.setOrganisation(updatedOrganisation);
            post.setUser(null);
        }
        else {
            user.setSubscription(savedSubscription);
            User updatedUser = userRepository.save(user);
            post.setUser(updatedUser);
            post.setOrganisation(null);
        }

        // Save the post details
        Post savedPost = postRepository.save(post);
        
        // Create a PostEvaluationDTO from the saved post details
        PostEvaluationDTO postEvaluation = PostEvaluationDTO.builder()
                                                            .hateSpeech(savedPost.getHateSpeech())
                                                            .originalContent(savedPost.getOriginalContent())
                                                            .redactedContent(savedPost.getRedactedContent())
                                                            .justification(savedPost.getJustification())
                                                            .build();
       
        return postEvaluation;
    }
}

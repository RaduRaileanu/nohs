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

    public Set<PostDTO> getPosts(User user){
        return postRepository.findByUser(user).stream()
                    .map(postMapper::postToPostDto)
                    .collect(Collectors.toSet());
    }

    @Override
    public PostEvaluationDTO checkPost(UserPrincipal principal, String origPost) throws Error {
        User user = userRepository.findById(principal.getUserId()).orElse(null);

        if(!(user.getSubscription() != null || (user.getOrganisation() != null && user.getOrganisation().getSubscription() != null))){
            throw new Error("The user or their company must have a subscription in order to check a post for hate speech");
        }

        Subscription subscription = user.getSubscription() != null ? user.getSubscription() : 
                                                                        user.getOrganisation().getSubscription();

        if(subscription.getTrialRequests() <= 0 || subscription.getBallance() <= 0){
            throw new Error("There aren't any funds to finance user request.");
        }

        ro.bynaus.nohs.entities.Service service = user.getOrganisation() != null ? 
                                                    user.getOrganisation().getSubscription().getService() :
                                                    user.getSubscription().getService();

        CheckPostInfo checkPostInfo = checkPostService.runPostCheck(service, origPost);

        Post post = postMapper.postDtoToPost(checkPostInfo.getPostDTO());

        if(subscription.getTrialRequests() > 0){
            subscription.setTrialRequests(subscription.getTrialRequests() - 1);
        }
        else {
            subscription.setBallance(subscription.getBallance() - checkPostInfo.getCost());
        }

        Subscription savedSubscription = subscriptionRepository.save(subscription);

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

        Post savedPost = postRepository.save(post);
        
        PostEvaluationDTO postEvaluation = PostEvaluationDTO.builder()
                                                            .hateSpeech(savedPost.getHateSpeech())
                                                            .originalContent(savedPost.getOriginalContent())
                                                            .redactedContent(savedPost.getRedactedContent())
                                                            .justification(savedPost.getJustification())
                                                            .build();
       
        return postEvaluation;
    }
}

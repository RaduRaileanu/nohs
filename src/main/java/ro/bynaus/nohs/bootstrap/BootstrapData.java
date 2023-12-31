package ro.bynaus.nohs.bootstrap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.entities.BillingInfo;
import ro.bynaus.nohs.entities.Organisation;
import ro.bynaus.nohs.entities.Payment;
import ro.bynaus.nohs.entities.Post;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.entities.Subscription;
import ro.bynaus.nohs.entities.User;
import ro.bynaus.nohs.mappers.OrganisationMapper;
import ro.bynaus.nohs.mappers.UserMapper;
import ro.bynaus.nohs.mappers.PostMapper;
import ro.bynaus.nohs.models.OrganisationDTO;
import ro.bynaus.nohs.models.PostDTO;
import ro.bynaus.nohs.models.UserDTO;
import ro.bynaus.nohs.repositories.BillingInfoRepository;
import ro.bynaus.nohs.repositories.OrganisationRepository;
import ro.bynaus.nohs.repositories.PaymentRepository;
import ro.bynaus.nohs.repositories.PostRepository;
import ro.bynaus.nohs.repositories.ServiceRepository;
import ro.bynaus.nohs.repositories.SubscriptionRepository;
import ro.bynaus.nohs.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner{

    //repositories
    private final ServiceRepository serviceRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final OrganisationRepository organisationRepository;
    private final BillingInfoRepository billingInfoRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    //mappers
    private final OrganisationMapper organisationMapper;
    private final UserMapper userMapper;
    private final PostMapper postMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // loadSubscriptions();
        // loadPayments();
        // loadOrganisations();
        // loadBillingInfo();
        // loadUser();
        loadPost();
        long serviceCount = serviceRepository.count();
        System.out.println("Services no: " + serviceCount);
        long paymentsCount = paymentRepository.count();
        System.out.println("Payments no: " + paymentsCount);
        long organisationsCount = organisationRepository.count();
        System.out.println("Organisations no: " + organisationsCount);
        long subscriptionCount = subscriptionRepository.count();
        System.out.println("Subscriptions no: " + subscriptionCount);
        long billingInfoCount = billingInfoRepository.count();
        System.out.println("Billing info no: " + billingInfoCount);
        long userCount = userRepository.count();
        System.out.println("Users no: " +userCount);

        Organisation organisation = organisationRepository.findFirstByOrderByIdAsc().orElse(null);
        
        BillingInfo billingInfo = billingInfoRepository.findFirstByOrderByIdAsc().orElse(null);

        organisation.setBillingInfo(billingInfo);
        OrganisationDTO orgDto = organisationMapper.organisationToOrganisationDTO(organisation);
        System.out.println(orgDto.getBillingInfo().getCity());
        System.out.println("Organisation's users count: " + organisation.getUsers().size());

        User user = userRepository.findFirstByOrderByIdAsc().orElse(null);
        System.out.println("User's organisation subscription ballance is " +
                        user.getOrganisation().getSubscription().getBallance());

        List<Post> userPosts = new ArrayList<>(user.getPosts());

        PostDTO userPostsDto = postMapper.postToPostDto(userPosts.get(0));

        System.out.println(userPostsDto.toString());
    }

    private void loadSubscriptions(){
        Service service = serviceRepository.findById(1).orElse(null);
        Subscription subscription = Subscription.builder()
                                                .service(service)
                                                .trialRequests(50)
                                                .ballance(5.0)
                                                .build();
                                                                
        subscriptionRepository.save(subscription);
    }

    private void loadPayments(){
        Payment payment1 = Payment.builder()
                                    .sum(5.00)
                                    .invoiceNo("TST001")
                                    .build();

        paymentRepository.save(payment1);
    }

    private void loadOrganisations(){
        // construct the set of payments for the new organisation
        Payment payment1 = paymentRepository.findFirstByOrderByIdAsc().orElse(null);
        Set<Payment> payments = new HashSet<Payment>();
        payments.add(payment1);

        // get the subscription for the new organisation
        Subscription subscription1 = subscriptionRepository.findFirstByOrderByIdAsc().orElse(null);

        // build the new organisation and save it to the repository
        Organisation organisation1 = Organisation.builder()
                                                    .name("Test Corp.")
                                                    .code("test_corp")
                                                    .payments(payments)
                                                    .subscription(subscription1)
                                                    .build();
        
        organisationRepository.save(organisation1);
    }

    private void loadBillingInfo(){
        BillingInfo billingInfo = BillingInfo.builder()
                                                .city("Testville")
                                                .country("Testonia")
                                                .street("Test Lake Avenue")
                                                .streetNo("11")
                                                .other("Apt 8")
                                                .taxNo("TST000123")
                                                .build();
                    
        billingInfoRepository.save(billingInfo);
    }

    private void loadUser(){
        Organisation organisation = organisationRepository.findFirstByOrderByIdAsc().orElse(null);
        // System.out.println("Retrievend organisation's users no: " + organisation.getUsers().size());
        // User user = User.builder()
        //                     .firstName("Testy")
        //                     .lastName("Testescu")
        //                     .email("ttestescu@testco.test")
        //                     .password("unsaltedpassword")
        //                     .role("admin")
        //                     .build();

        // Set<User> orgUsers = organisation.getUsers();
        // orgUsers.add(user);
        // organisation.setUsers(orgUsers);
        // Organisation savedOrganisation = organisationRepository.saveAndFlush(organisation);
        // System.out.println("Saved org id" + savedOrganisation.getId());
        // user.setOrganisation(savedOrganisation);
        // userRepository.save(user);
        // organisationRepository.save(savedOrganisation);

        // OrganisationDTO orgDto = organisationMapper.organisationToOrganisationDTO(savedOrganisation);
        // System.out.println(orgDto.getUsers().size());

        
        OrganisationDTO orgDTO = organisationMapper.organisationToOrganisationDTO(organisation);

        UserDTO userDto = UserDTO.builder()
                                    .firstName("Testy")
                                    .lastName("Testescu")
                                    .email("ttestescu@testco.test")
                                    .password("unsaltedpassword")
                                    .role("admin")
                                    .build();
        
        

        // Set<UserDTO> orgUsers = orgDTO.getUsers();
        // orgUsers.add(userDto);
        // orgDTO.setUsers(orgUsers);

        // userDto.setOrganisation(orgDTO);

        User user = userMapper.userDtoToUser(userDto);

        Set<User> orgUsers = organisation.getUsers();
        orgUsers.add(user);
        organisation.setUsers(orgUsers);
        Organisation savedOrg = organisationRepository.saveAndFlush(organisation);

        user.setOrganisation(savedOrg);
        // Organisation updatedOrganisation = organisationMapper.organisationDTOToOrganisation(orgDTO);

        userRepository.save(user);
        // organisationRepository.saveAndFlush(organisation);
    }

    private void loadPost(){
        User user = userRepository.findFirstByOrderByIdAsc().orElse(null);
        UserDTO userDto = userMapper.userToUserDto(user);

        PostDTO postDTO = PostDTO.builder()
                                    .originalContent("I hate all Romanians. But I'm learning Spring!")
                                    .build();
        
        String hateContent = "I hate all Romanians";
        String replacement = "***";
        String justification = "Affirming hatred towards a nation is not protected by freedom of speech";

        String redactedContent = postDTO.getOriginalContent().replace(hateContent, replacement);

        postDTO.setRedactedContent(redactedContent);
        postDTO.setHateSpeech(true);
        postDTO.setJustification(justification);
        // postDTO.setOrganisationDto(null);

        // postDTO.setUserDto(userDto);

        Post post = postMapper.postDtoToPost(postDTO);
        // System.out.println(post.getOrganisation().toString());
        // System.out.println("post.getOrganisation().toString()");
        // post.setUser(user);

        Set<Post> userPosts = user.getPosts();
        userPosts.add(post);
        user.setPosts(userPosts);

        User savedUser = userRepository.saveAndFlush(user);

        post.setOrganisation(null);
        post.setUser(savedUser);

        postRepository.save(post);
        // userRepository.saveAndFlush(user);
    }
}

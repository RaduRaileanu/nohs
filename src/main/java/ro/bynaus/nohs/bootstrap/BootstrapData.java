package ro.bynaus.nohs.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ro.bynaus.nohs.domain.Organisation;
import ro.bynaus.nohs.repository.OrganisationRepository;

@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {
    
    private final OrganisationRepository organisationRepository;

    @Override
    public void run(String... args) throws Exception{
        loadOrganisationData();

        organisationRepository.count().subscribe(count -> {
            System.out.println("We have " + count + " organisations enrolled");
        });
    }

    private void loadOrganisationData(){
        // create dummy organisations if db doesn't contain any
        organisationRepository.count().subscribe(count -> {
            if(count == 0){
                Organisation org1 = Organisation.builder()
                                                .name("Test Inc.")
                                                .code("test_inc")
                                                .serviceType("platinum")
                                                .trialRequests(10)
                                                .build();
                Organisation org2 = Organisation.builder()
                                                .name("Acme Testing")
                                                .code("acme_testing")
                                                .serviceType("premium")
                                                .trialRequests(7)
                                                .build();
                Organisation org3 = Organisation.builder()
                                                .name("The LowB Co")
                                                .code("lowb_co")
                                                .serviceType("basic")
                                                .trialRequests(10)
                                                .build();

                // save the 3 organisations to the DB 
                organisationRepository.save(org1).subscribe();
                organisationRepository.save(org2).subscribe();
                organisationRepository.save(org3).subscribe();
            }
        });   
    }
}

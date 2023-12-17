package ro.bynaus.nohs.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import ro.bynaus.nohs.config.DBConfig;
import ro.bynaus.nohs.domain.Organisation;

@DataR2dbcTest
@Import(DBConfig.class)
class OrganisationRepositoryTest {
    @Autowired
    private OrganisationRepository organisationRepository;

    @Test
    void saveNewOrganisation(){
        organisationRepository.save(getTestOrganisation())
            .subscribe(organisation -> {
                System.out.println(organisation.toString());
            });
    }

    Organisation getTestOrganisation(){
        return Organisation.builder().name("Test Inc.")
                                    .code("test_inc")
                                    .serviceType("full")
                                    .trialRequests(10)
                                    .build();
    }
}

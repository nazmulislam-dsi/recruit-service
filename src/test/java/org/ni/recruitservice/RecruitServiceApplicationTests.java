package org.ni.recruitservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.ni.recruitservice.controller.OfferControllerIntegrationTest;
import org.ni.recruitservice.service.ApplicationServiceUnitTest;
import org.ni.recruitservice.service.OfferServiceUnitTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationServiceUnitTest.class,
        OfferServiceUnitTest.class,
        OfferControllerIntegrationTest.class
})
public class RecruitServiceApplicationTests {


    @Test
    public void contextLoads() {
    }
}

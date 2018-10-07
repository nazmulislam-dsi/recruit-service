package org.ni.recruitservice.service;


import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ni.recruitservice.dto.ApplicationPatchDto;
import org.ni.recruitservice.dto.ApplicationPostDto;
import org.ni.recruitservice.enums.ApplicationStatus;
import org.ni.recruitservice.model.Application;
import org.ni.recruitservice.model.Offer;
import org.ni.recruitservice.repository.ApplicationRepository;
import org.ni.recruitservice.repository.OfferRepository;
import org.ni.recruitservice.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder
public class ApplicationServiceUnitTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private OfferService offerService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Before
    public void setup() {
        applicationRepository.deleteAll();
        offerRepository.deleteAll();
    }

    @Test
    public void saveOrUpdateApplicationTest(){
        Offer offer = new Offer();
        offer.setTitle("A sample offer");
        offer.setStartDate(new Date());
        offer = offerService.saveOrUpdateOffer(offer);
        assertNotNull(offer.getId());
        Application application = new Application();
        application.setResume("Test");
        application.setOffer(offer);
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application.setCandidateEmail("naim.5221@gmail.com");
        application = applicationService.saveOrUpdateApplication(application);
        assertNotNull(application.getId());
    }

    @Test
    public void updateApplicationByApplicationIdTest(){
        Offer offer = new Offer();
        offer.setTitle("A sample offer");
        offer.setStartDate(new Date());
        offer = offerService.saveOrUpdateOffer(offer);
        assertNotNull(offer.getId());
        Application application = new Application();
        application.setResume("Test");
        application.setOffer(offer);
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application.setCandidateEmail("naim.5221@gmail.com");
        application = applicationService.saveOrUpdateApplication(application);
        assertNotNull(application.getId());
        application.setApplicationStatus(ApplicationStatus.HIRED);
        application = applicationService.updateApplicationByApplicationId(application.getId(), Commons.transformObject(application,ApplicationPatchDto.class));
        assertEquals(ApplicationStatus.HIRED,application.getApplicationStatus());
        application.setApplicationStatus(ApplicationStatus.INVITED);
        application = applicationService.updateApplicationByApplicationId(application.getId(), Commons.transformObject(application,ApplicationPatchDto.class));
        assertEquals(ApplicationStatus.INVITED,application.getApplicationStatus());
        application.setApplicationStatus(ApplicationStatus.REJECTED);
        application = applicationService.updateApplicationByApplicationId(application.getId(), Commons.transformObject(application,ApplicationPatchDto.class));
        assertEquals(ApplicationStatus.REJECTED,application.getApplicationStatus());
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application = applicationService.updateApplicationByApplicationId(application.getId(), Commons.transformObject(application,ApplicationPatchDto.class));
        assertEquals(ApplicationStatus.APPLIED,application.getApplicationStatus());
    }

    @Test
    public void getApplicationListByOfferIdTest(){
        Offer offer = new Offer();
        offer.setTitle("A sample offer again");
        offer.setStartDate(new Date());
        offer = offerService.saveOrUpdateOffer(offer);
        assertNotNull(offer.getId());
        Application application = new Application();
        application.setResume("Test");
        application.setOffer(offer);
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application.setCandidateEmail("naim5221@gmail.com");
        application = applicationService.saveOrUpdateApplication(application);
        assertNotNull(application.getId());
        List<Application> applicationList = applicationService.getApplicationListByOfferId(offer.getId());
        assertEquals(1, applicationList.size());

    }

    @Test
    public void getApplicationListByApplicationIdAndOfferIdTest(){
        Offer offer = new Offer();
        offer.setTitle("A sample offer again");
        offer.setStartDate(new Date());
        offer = offerService.saveOrUpdateOffer(offer);
        assertNotNull(offer.getId());
        Application application = new Application();
        application.setResume("Test");
        application.setOffer(offer);
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application.setCandidateEmail("naim5221@gmail.com");
        application = applicationService.saveOrUpdateApplication(application);
        assertNotNull(application.getId());
        List<Application> applicationList = applicationService.getApplicationListByOfferId(offer.getId());
        assertEquals(1, applicationList.size());
        Application applicationAgain = applicationService.getApplicationListByApplicationIdAndOfferId(offer.getId(),application.getId());
        assertNotNull(applicationAgain);
        assertEquals(applicationAgain.getCandidateEmail(), application.getCandidateEmail());
    }
}

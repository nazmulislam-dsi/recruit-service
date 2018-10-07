package org.ni.recruitservice.service;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ni.recruitservice.model.Offer;
import org.ni.recruitservice.repository.ApplicationRepository;
import org.ni.recruitservice.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder
public class OfferServiceUnitTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private OfferService offerService;

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
    public void saveOrUpdateOfferTest() {
        Offer offer = new Offer();
        offer.setTitle("A sample offer");
        offer.setStartDate(new Date());
        offer = offerService.saveOrUpdateOffer(offer);
        assertNotNull(offer.getId());
    }

    @Test
    public void getAllOffersTest() {
        Offer offer = new Offer();
        offer.setTitle("A sample offer");
        offer.setStartDate(new Date());
        offer = offerService.saveOrUpdateOffer(offer);
        assertNotNull(offer.getId());
        List<Offer> offerList = offerService.getAllOffers();
        assertEquals(1, offerList.size());
    }

    @Test
    public void getOfferByOfferIdTest(){
        Offer offer = new Offer();
        offer.setTitle("A sample offer again");
        offer.setStartDate(new Date());
        offer = offerService.saveOrUpdateOffer(offer);
        assertNotNull(offer.getId());
        Offer offerAgain = offerService.getOfferByOfferId(offer.getId());
        assertNotNull(offerAgain);
        assertEquals(offerAgain.getTitle(), offer.getTitle());
    }
}

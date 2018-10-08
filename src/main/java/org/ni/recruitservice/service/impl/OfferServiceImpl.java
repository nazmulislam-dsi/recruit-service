package org.ni.recruitservice.service.impl;

import org.ni.recruitservice.model.Offer;
import org.ni.recruitservice.repository.OfferRepository;
import org.ni.recruitservice.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by nazmul on 10/6/2018.
 */
@Service
@Transactional
public class OfferServiceImpl implements OfferService {

    private OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    /**
     * Get all offer from database
     *
     * @return
     */
    @Override
    public List<Offer> getAllOffers() {
        try {
            return offerRepository.findAll();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Save or update any offer
     *
     * @param offer
     * @return
     */
    @Override
    public Offer saveOrUpdateOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    /**
     * Get offer from offerId
     *
     * @param offerId
     * @return
     */
    @Override
    public Offer getOfferByOfferId(Long offerId) {
        return offerRepository.findFirstById(offerId);
    }
}

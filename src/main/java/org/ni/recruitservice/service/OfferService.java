package org.ni.recruitservice.service;

import org.ni.recruitservice.model.Offer;

import java.util.List;

/**
 * Created by nazmul on 10/6/2018.
 */

public interface OfferService {
    List<Offer> getAllOffers();
    Offer saveOrUpdateOffer(Offer offer);
    Offer getOfferByOfferId(Long offerId);
}

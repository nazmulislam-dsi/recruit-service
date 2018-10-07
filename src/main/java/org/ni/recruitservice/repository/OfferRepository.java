package org.ni.recruitservice.repository;

import org.ni.recruitservice.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nazmul on 10/6/2018.
 */
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    Offer findFirstById(Long offerId);
}

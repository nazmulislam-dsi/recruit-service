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
//   @Query("SELECT o, COUNT(a) as numberOfApplications FROM Offer o JOIN FETCH o.applications a GROUP BY o.title")
//    @Query("SELECT o, COUNT(a) as numberOfApplications FROM Offer o JOIN o.applications a GROUP BY o.title")
//    List<Offer> getAllOffers();
    Offer findFirstById(Long offerId);
}

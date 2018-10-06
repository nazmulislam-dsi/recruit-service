package org.ni.recruitservice.repository;

import org.ni.recruitservice.model.Application;
import org.ni.recruitservice.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nazmul on 10/6/2018.
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findApplicationByOffer(Offer offer);
    Application findFirstByOfferAndId(Offer offer, Long applicationId);
    Application findFirstById(Long applicationId);
}

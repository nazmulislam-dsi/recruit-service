package org.ni.recruitservice.service.impl;

import org.ni.recruitservice.dto.ApplicationPostDto;
import org.ni.recruitservice.model.Application;
import org.ni.recruitservice.model.Offer;
import org.ni.recruitservice.repository.ApplicationRepository;
import org.ni.recruitservice.service.ApplicationService;
import org.ni.recruitservice.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by nazmul on 10/6/2018.
 */
@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
    private ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Application saveOrUpdateApplication(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public Application saveOrUpdateApplication(ApplicationPostDto applicationPostDto, Long offerId) throws Exception {
        Offer offer = new Offer();
        offer.setId(offerId);
        Application application = Commons.transformObject(applicationPostDto,Application.class);
        application.setOffer(offer);
        return saveOrUpdateApplication(application);
    }

}

package org.ni.recruitservice.service.impl;

import org.ni.recruitservice.dto.ApplicationPatchDto;
import org.ni.recruitservice.dto.ApplicationPostDto;
import org.ni.recruitservice.enums.ApplicationStatus;
import org.ni.recruitservice.model.Application;
import org.ni.recruitservice.model.Offer;
import org.ni.recruitservice.repository.ApplicationRepository;
import org.ni.recruitservice.service.ApplicationService;
import org.ni.recruitservice.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    /**
     * Save or update Application
     *
     * @param application
     * @return
     */
    @Override
    public Application saveOrUpdateApplication(Application application) {
        try {
            return applicationRepository.save(application);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Save or update Application
     *
     * @param applicationPostDto
     * @param offerId
     * @return
     */
    @Override
    public Application saveOrUpdateApplication(ApplicationPostDto applicationPostDto, Long offerId) {
        try {
            Offer offer = new Offer();
            offer.setId(offerId);
            Application application = Commons.transformObject(applicationPostDto, Application.class);
            application.setOffer(offer);
            return saveOrUpdateApplication(application);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Get application list with offerId
     *
     * @param offerId
     * @return
     */
    @Override
    public List<Application> getApplicationListByOfferId(Long offerId) {
        try {
            Offer offer = new Offer();
            offer.setId(offerId);
            return applicationRepository.findApplicationByOffer(offer);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Get single application with applicationId and offerId
     *
     * @param offerId
     * @param applicationId
     * @return
     */
    @Override
    public Application getApplicationListByApplicationIdAndOfferId(Long offerId, Long applicationId) {
        try {
            Offer offer = new Offer();
            offer.setId(offerId);
            return applicationRepository.findFirstByOfferAndId(offer,applicationId);
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Update application atatus and send notification, for different type of status update it wil call different implementation of NotificationService
     *
     * @param applicationId
     * @param applicationPatchDto
     * @return
     */
    @Override
    public Application updateApplicationByApplicationId(Long applicationId, ApplicationPatchDto applicationPatchDto) {
        Application application = applicationRepository.findFirstById(applicationId);
        boolean changed = true;
        if(!Commons.isEmpty(applicationPatchDto.getResume())){
            application.setResume(applicationPatchDto.getResume());
            changed = true;
        }
        if(!applicationPatchDto.getApplicationStatus().equals(application.getApplicationStatus())) {
            application.setApplicationStatus(applicationPatchDto.getApplicationStatus());
            changed = true;
        }
        if(changed) {
            application = applicationRepository.save(application);
            application.getApplicationStatus().getNotificationService().sendNotification(application);
        }
        return application;
    }

}

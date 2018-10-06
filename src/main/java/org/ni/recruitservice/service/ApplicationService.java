package org.ni.recruitservice.service;

import org.ni.recruitservice.dto.ApplicationPostDto;
import org.ni.recruitservice.model.Application;

import java.util.List;

/**
 * Created by nazmul on 10/6/2018.
 */
public interface ApplicationService {
    Application saveOrUpdateApplication(Application offer);
    Application saveOrUpdateApplication(ApplicationPostDto applicationPostDto, Long offerId);
    List<Application> getApplicationListByOfferId(Long offerId);
    Application getApplicationListByApplicationIdAndOfferId(Long offerId, Long applicationId);
}

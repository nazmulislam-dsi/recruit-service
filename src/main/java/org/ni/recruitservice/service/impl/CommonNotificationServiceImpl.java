package org.ni.recruitservice.service.impl;

import org.ni.recruitservice.model.Application;
import org.ni.recruitservice.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nazmul on 10/7/2018.
 */
public class CommonNotificationServiceImpl implements NotificationService {
    private static Logger log = LoggerFactory.getLogger(CommonNotificationServiceImpl.class);
    private static NotificationService singleton = null;

    public synchronized static NotificationService getInstance() {
        if (singleton == null) singleton = new CommonNotificationServiceImpl();
        return singleton;
    }

    @Override
    public boolean sendNotification(Application application) {
        log.info("Hello, "+ application.getCandidateEmail() + " your current application status is : " + application.getApplicationStatus().toString());
        return true;
    }
}

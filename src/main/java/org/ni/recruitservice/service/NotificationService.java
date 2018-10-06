package org.ni.recruitservice.service;

import org.ni.recruitservice.model.Application;

/**
 * Created by nazmul on 10/7/2018.
 */
public interface NotificationService {
    boolean sendNotification(Application application);
}

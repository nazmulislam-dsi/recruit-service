package org.ni.recruitservice.enums;

import org.ni.recruitservice.service.NotificationService;
import org.ni.recruitservice.service.impl.*;

/**
 * Created by nazmul on 10/6/2018.
 */
public enum ApplicationStatus {
    APPLIED(AppliedNotificationServiceImpl.getInstance()),
    INVITED(InvitedNotificationServiceImpl.getInstance()),
    REJECTED(RejectedNotificationServiceImpl.getInstance()),
    HIRED(HiredNotificationServiceImpl.getInstance());

    private NotificationService notificationService;

    private ApplicationStatus(NotificationService s) {
        notificationService = s;
    }

    /*public static NotificationService getNotificationService(ApplicationStatus applicationStatus){
        switch (applicationStatus){
            case HIRED:
                return HiredNotificationServiceImpl.getInstance();
            case APPLIED:
                return AppliedNotificationServiceImpl.getInstance();
            case INVITED:
                return InvitedNotificationServiceImpl.getInstance();
            case REJECTED:
                return RejectedNotificationServiceImpl.getInstance();
            default:
                return CommonNotificationServiceImpl.getInstance();
        }
    }*/

    public NotificationService getNotificationService(){
        return notificationService;
    }
}
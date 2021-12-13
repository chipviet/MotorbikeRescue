package com.chipviet.project.service.dto;

import org.springframework.stereotype.Service;

@Service
public class PushNotificationDTO {

    private String notification;

    private String phoneNumber;

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

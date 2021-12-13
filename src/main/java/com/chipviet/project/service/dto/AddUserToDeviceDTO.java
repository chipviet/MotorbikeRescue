package com.chipviet.project.service.dto;

import com.chipviet.project.domain.User;
import org.springframework.stereotype.Service;

@Service
public class AddUserToDeviceDTO {

    private String deviceUuid;

    private User user;

    private String usedBy;

    public String getDeviceUuid() {
        return deviceUuid;
    }

    public void setDeviceUuid(String deviceUuid) {
        this.deviceUuid = deviceUuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(String usedBy) {
        this.usedBy = usedBy;
    }
}

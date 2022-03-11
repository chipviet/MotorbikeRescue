package com.chipviet.project.service.dto;

import com.chipviet.project.domain.User;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class ConfirmDTO {

    private Long connectionId;

    public Long getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Long connectionId) {
        this.connectionId = connectionId;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "connectionId='" + connectionId + '\'' +
            "}";
    }
}

package com.chipviet.project.service.dto;

import com.chipviet.project.domain.User;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class ConfirmDTO {

    private Long requestId;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "requestId='" + requestId + '\'' +
            "}";
    }
}

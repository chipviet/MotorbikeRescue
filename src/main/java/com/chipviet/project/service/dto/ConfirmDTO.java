package com.chipviet.project.service.dto;

import com.chipviet.project.domain.User;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class ConfirmDTO {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            "}";
    }
}

package com.volleyhooks.volley.models;

/**
 * Request to create an organization.
 */
public class CreateOrganizationRequest {
    private String name;

    public CreateOrganizationRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


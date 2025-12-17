package com.volleyhooks.volley.models;

import java.util.List;

/**
 * Response from listing organizations.
 */
public class ListOrganizationsResponse {
    private List<Organization> organizations;

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
}


package com.volleyhooks.volley;

import com.volleyhooks.volley.models.*;
import okhttp3.Request;

import java.util.List;

/**
 * Organizations API methods.
 */
public class Organizations {
    private final VolleyClient client;

    Organizations(VolleyClient client) {
        this.client = client;
    }

    /**
     * Lists all organizations the user has access to.
     */
    public List<Organization> list() throws VolleyException {
        Request request = client.buildRequest("GET", "/api/org/list", null).build();
        ListOrganizationsResponse response = client.executeRequest(request, ListOrganizationsResponse.class);
        return response != null ? response.getOrganizations() : null;
    }

    /**
     * Gets the current organization.
     *
     * @param organizationId Optional organization ID. If null, uses default organization.
     */
    public Organization get(Long organizationId) throws VolleyException {
        Long originalOrgId = client.getOrganizationId();
        if (organizationId != null) {
            client.setOrganizationId(organizationId);
        }

        try {
            Request request = client.buildRequest("GET", "/api/org", null).build();
            return client.executeRequest(request, Organization.class);
        } finally {
            client.setOrganizationId(originalOrgId);
        }
    }

    /**
     * Creates a new organization.
     */
    public Organization create(CreateOrganizationRequest request) throws VolleyException {
        Request httpRequest = client.buildRequest("POST", "/api/org", request).build();
        return client.executeRequest(httpRequest, Organization.class);
    }
}


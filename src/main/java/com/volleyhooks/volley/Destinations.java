package com.volleyhooks.volley;

import com.volleyhooks.volley.models.*;
import okhttp3.Request;

import java.util.List;

/**
 * Destinations API methods.
 */
public class Destinations {
    private final VolleyClient client;

    Destinations(VolleyClient client) {
        this.client = client;
    }

    /**
     * Lists all destinations in a project.
     */
    public List<Destination> list(Long projectId) throws VolleyException {
        Request request = client.buildRequest("GET", "/api/projects/" + projectId + "/destinations", null).build();
        ListDestinationsResponse response = client.executeRequest(request, ListDestinationsResponse.class);
        return response != null ? response.getDestinations() : null;
    }

    /**
     * Creates a new destination.
     */
    public Destination create(Long projectId, CreateDestinationRequest request) throws VolleyException {
        Request httpRequest = client.buildRequest("POST", "/api/projects/" + projectId + "/destinations", request).build();
        CreateDestinationResponse response = client.executeRequest(httpRequest, CreateDestinationResponse.class);
        return response != null ? response.getDestination() : null;
    }

    /**
     * Gets details of a specific destination.
     */
    public Destination get(Long destinationId) throws VolleyException {
        Request request = client.buildRequest("GET", "/api/destinations/" + destinationId, null).build();
        GetDestinationResponse response = client.executeRequest(request, GetDestinationResponse.class);
        return response != null ? response.getDestination() : null;
    }

    /**
     * Updates a destination.
     */
    public Destination update(Long destinationId, CreateDestinationRequest request) throws VolleyException {
        Request httpRequest = client.buildRequest("PUT", "/api/destinations/" + destinationId, request).build();
        CreateDestinationResponse response = client.executeRequest(httpRequest, CreateDestinationResponse.class);
        return response != null ? response.getDestination() : null;
    }

    /**
     * Deletes a destination.
     */
    public void delete(Long destinationId) throws VolleyException {
        Request request = client.buildRequest("DELETE", "/api/destinations/" + destinationId, null).build();
        client.executeRequest(request);
    }
}


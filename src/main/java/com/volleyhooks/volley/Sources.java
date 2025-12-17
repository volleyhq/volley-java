package com.volleyhooks.volley;

import com.volleyhooks.volley.models.*;
import okhttp3.Request;

import java.util.List;

/**
 * Sources API methods.
 */
public class Sources {
    private final VolleyClient client;

    Sources(VolleyClient client) {
        this.client = client;
    }

    /**
     * Lists all sources in a project.
     */
    public List<Source> list(Long projectId) throws VolleyException {
        Request request = client.buildRequest("GET", "/api/projects/" + projectId + "/sources", null).build();
        ListSourcesResponse response = client.executeRequest(request, ListSourcesResponse.class);
        return response != null ? response.getSources() : null;
    }

    /**
     * Creates a new source.
     */
    public Source create(Long projectId, CreateSourceRequest request) throws VolleyException {
        Request httpRequest = client.buildRequest("POST", "/api/projects/" + projectId + "/sources", request).build();
        CreateSourceResponse response = client.executeRequest(httpRequest, CreateSourceResponse.class);
        return response != null ? response.getSource() : null;
    }

    /**
     * Gets details of a specific source.
     */
    public Source get(Long sourceId) throws VolleyException {
        Request request = client.buildRequest("GET", "/api/sources/" + sourceId, null).build();
        GetSourceResponse response = client.executeRequest(request, GetSourceResponse.class);
        return response != null ? response.getSource() : null;
    }

    /**
     * Updates a source.
     */
    public Source update(Long sourceId, CreateSourceRequest request) throws VolleyException {
        Request httpRequest = client.buildRequest("PUT", "/api/sources/" + sourceId, request).build();
        CreateSourceResponse response = client.executeRequest(httpRequest, CreateSourceResponse.class);
        return response != null ? response.getSource() : null;
    }

    /**
     * Deletes a source.
     */
    public void delete(Long sourceId) throws VolleyException {
        Request request = client.buildRequest("DELETE", "/api/sources/" + sourceId, null).build();
        client.executeRequest(request);
    }
}


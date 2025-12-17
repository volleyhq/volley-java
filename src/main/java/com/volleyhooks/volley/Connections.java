package com.volleyhooks.volley;

import com.volleyhooks.volley.models.*;
import okhttp3.Request;

/**
 * Connections API methods.
 */
public class Connections {
    private final VolleyClient client;

    Connections(VolleyClient client) {
        this.client = client;
    }

    /**
     * Creates a connection between a source and destination.
     */
    public Connection create(Long projectId, CreateConnectionRequest request) throws VolleyException {
        Request httpRequest = client.buildRequest("POST", "/api/projects/" + projectId + "/connections", request).build();
        CreateConnectionResponse response = client.executeRequest(httpRequest, CreateConnectionResponse.class);
        return response != null ? response.getConnection() : null;
    }

    /**
     * Gets details and metrics for a connection.
     */
    public Connection get(Long connectionId) throws VolleyException {
        Request request = client.buildRequest("GET", "/api/connections/" + connectionId, null).build();
        GetConnectionResponse response = client.executeRequest(request, GetConnectionResponse.class);
        return response != null ? response.getConnection() : null;
    }

    /**
     * Updates a connection.
     */
    public Connection update(Long connectionId, CreateConnectionRequest request) throws VolleyException {
        Request httpRequest = client.buildRequest("PUT", "/api/connections/" + connectionId, request).build();
        CreateConnectionResponse response = client.executeRequest(httpRequest, CreateConnectionResponse.class);
        return response != null ? response.getConnection() : null;
    }

    /**
     * Deletes a connection.
     */
    public void delete(Long connectionId) throws VolleyException {
        Request request = client.buildRequest("DELETE", "/api/connections/" + connectionId, null).build();
        client.executeRequest(request);
    }
}


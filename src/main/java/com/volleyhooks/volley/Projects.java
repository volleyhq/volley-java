package com.volleyhooks.volley;

import com.volleyhooks.volley.models.*;
import okhttp3.Request;

import java.util.List;

/**
 * Projects API methods.
 */
public class Projects {
    private final VolleyClient client;

    Projects(VolleyClient client) {
        this.client = client;
    }

    /**
     * Lists all projects in the current organization.
     */
    public List<Project> list() throws VolleyException {
        Request request = client.buildRequest("GET", "/api/projects", null).build();
        ListProjectsResponse response = client.executeRequest(request, ListProjectsResponse.class);
        return response != null ? response.getProjects() : null;
    }

    /**
     * Creates a new project.
     */
    public Project create(CreateProjectRequest request) throws VolleyException {
        Request httpRequest = client.buildRequest("POST", "/api/projects", request).build();
        CreateProjectResponse response = client.executeRequest(httpRequest, CreateProjectResponse.class);
        return response != null ? response.getProject() : null;
    }

    /**
     * Updates a project's name.
     */
    public Project update(Long projectId, UpdateProjectRequest request) throws VolleyException {
        Request httpRequest = client.buildRequest("PUT", "/api/projects/" + projectId, request).build();
        CreateProjectResponse response = client.executeRequest(httpRequest, CreateProjectResponse.class);
        return response != null ? response.getProject() : null;
    }

    /**
     * Deletes a project.
     */
    public void delete(Long projectId) throws VolleyException {
        Request request = client.buildRequest("DELETE", "/api/projects/" + projectId, null).build();
        client.executeRequest(request);
    }

    /**
     * Lists all connections in a project.
     */
    public List<Connection> getConnections(Long projectId) throws VolleyException {
        Request request = client.buildRequest("GET", "/api/projects/" + projectId + "/connections", null).build();
        ListConnectionsResponse response = client.executeRequest(request, ListConnectionsResponse.class);
        return response != null ? response.getConnections() : null;
    }
}


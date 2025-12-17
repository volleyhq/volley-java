package com.volleyhooks.volley.models;

/**
 * Request to update a project.
 */
public class UpdateProjectRequest {
    private String name;

    public UpdateProjectRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


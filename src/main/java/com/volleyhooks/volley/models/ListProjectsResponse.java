package com.volleyhooks.volley.models;

import java.util.List;

/**
 * Response from listing projects.
 */
public class ListProjectsResponse {
    private List<Project> projects;

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}


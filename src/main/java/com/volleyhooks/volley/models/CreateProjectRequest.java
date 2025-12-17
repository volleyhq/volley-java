package com.volleyhooks.volley.models;

/**
 * Request to create a project.
 */
public class CreateProjectRequest {
    private String name;
    private Boolean isDefault;

    public CreateProjectRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
}


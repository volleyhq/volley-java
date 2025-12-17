package com.volleyhooks.volley.models;

/**
 * Request to create a source.
 */
public class CreateSourceRequest {
    private String name;
    private Integer eps;
    private String authType;

    public CreateSourceRequest(String name, Integer eps, String authType) {
        this.name = name;
        this.eps = eps;
        this.authType = authType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEps() {
        return eps;
    }

    public void setEps(Integer eps) {
        this.eps = eps;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }
}


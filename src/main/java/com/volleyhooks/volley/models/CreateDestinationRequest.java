package com.volleyhooks.volley.models;

/**
 * Request to create a destination.
 */
public class CreateDestinationRequest {
    private String name;
    private String url;
    private Integer eps;

    public CreateDestinationRequest(String name, String url, Integer eps) {
        this.name = name;
        this.url = url;
        this.eps = eps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getEps() {
        return eps;
    }

    public void setEps(Integer eps) {
        this.eps = eps;
    }
}


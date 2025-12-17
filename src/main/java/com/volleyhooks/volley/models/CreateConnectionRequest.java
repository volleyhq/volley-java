package com.volleyhooks.volley.models;

/**
 * Request to create a connection.
 */
public class CreateConnectionRequest {
    private Long sourceId;
    private Long destinationId;
    private String status;
    private Integer eps;
    private Integer maxRetries;

    public CreateConnectionRequest(Long sourceId, Long destinationId, String status, Integer eps, Integer maxRetries) {
        this.sourceId = sourceId;
        this.destinationId = destinationId;
        this.status = status;
        this.eps = eps;
        this.maxRetries = maxRetries;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getEps() {
        return eps;
    }

    public void setEps(Integer eps) {
        this.eps = eps;
    }

    public Integer getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(Integer maxRetries) {
        this.maxRetries = maxRetries;
    }
}


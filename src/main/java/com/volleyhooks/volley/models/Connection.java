package com.volleyhooks.volley.models;

import java.time.Instant;

/**
 * Represents a connection between a source and destination.
 */
public class Connection {
    private Long id;
    private Long sourceId;
    private Long destinationId;
    private String status;
    private Integer eps;
    private Integer maxRetries;
    private Instant createdAt;
    private Instant updatedAt;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSourceId() { return sourceId; }
    public void setSourceId(Long sourceId) { this.sourceId = sourceId; }

    public Long getDestinationId() { return destinationId; }
    public void setDestinationId(Long destinationId) { this.destinationId = destinationId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getEps() { return eps; }
    public void setEps(Integer eps) { this.eps = eps; }

    public Integer getMaxRetries() { return maxRetries; }
    public void setMaxRetries(Integer maxRetries) { this.maxRetries = maxRetries; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}


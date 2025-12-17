package com.volleyhooks.volley.models;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Represents a webhook event/request.
 */
public class Event {
    private Long id;
    private String eventId;
    private Long sourceId;
    private Long projectId;
    private String rawBody;
    private Map<String, Object> headers;
    private String status;
    private List<DeliveryAttempt> deliveryAttempts;
    private Instant createdAt;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public Long getSourceId() { return sourceId; }
    public void setSourceId(Long sourceId) { this.sourceId = sourceId; }

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }

    public String getRawBody() { return rawBody; }
    public void setRawBody(String rawBody) { this.rawBody = rawBody; }

    public Map<String, Object> getHeaders() { return headers; }
    public void setHeaders(Map<String, Object> headers) { this.headers = headers; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<DeliveryAttempt> getDeliveryAttempts() { return deliveryAttempts; }
    public void setDeliveryAttempts(List<DeliveryAttempt> deliveryAttempts) { this.deliveryAttempts = deliveryAttempts; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}


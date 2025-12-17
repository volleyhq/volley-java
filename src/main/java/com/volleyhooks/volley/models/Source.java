package com.volleyhooks.volley.models;

import java.time.Instant;

/**
 * Represents a webhook source.
 */
public class Source {
    private Long id;
    private String slug;
    private String ingestionId;
    private String type;
    private Integer eps;
    private String status;
    private Long connectionCount;
    private String authType;
    private Boolean verifySignature;
    private Boolean webhookSecretSet;
    private String authUsername;
    private String authKeyName;
    private Instant createdAt;
    private Instant updatedAt;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getIngestionId() { return ingestionId; }
    public void setIngestionId(String ingestionId) { this.ingestionId = ingestionId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getEps() { return eps; }
    public void setEps(Integer eps) { this.eps = eps; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getConnectionCount() { return connectionCount; }
    public void setConnectionCount(Long connectionCount) { this.connectionCount = connectionCount; }

    public String getAuthType() { return authType; }
    public void setAuthType(String authType) { this.authType = authType; }

    public Boolean getVerifySignature() { return verifySignature; }
    public void setVerifySignature(Boolean verifySignature) { this.verifySignature = verifySignature; }

    public Boolean getWebhookSecretSet() { return webhookSecretSet; }
    public void setWebhookSecretSet(Boolean webhookSecretSet) { this.webhookSecretSet = webhookSecretSet; }

    public String getAuthUsername() { return authUsername; }
    public void setAuthUsername(String authUsername) { this.authUsername = authUsername; }

    public String getAuthKeyName() { return authKeyName; }
    public void setAuthKeyName(String authKeyName) { this.authKeyName = authKeyName; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}


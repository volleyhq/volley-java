package com.volleyhooks.volley.models;

import java.util.List;

/**
 * Response from listing delivery attempts.
 */
public class ListDeliveryAttemptsResponse {
    private List<DeliveryAttempt> attempts;
    private Long total;
    private Integer limit;
    private Integer offset;

    public List<DeliveryAttempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<DeliveryAttempt> attempts) {
        this.attempts = attempts;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}


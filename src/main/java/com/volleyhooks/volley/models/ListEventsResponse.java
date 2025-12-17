package com.volleyhooks.volley.models;

import java.util.List;

/**
 * Response from listing events.
 */
public class ListEventsResponse {
    private List<Event> requests;
    private Long total;
    private Integer limit;
    private Integer offset;

    public List<Event> getRequests() {
        return requests;
    }

    public void setRequests(List<Event> requests) {
        this.requests = requests;
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


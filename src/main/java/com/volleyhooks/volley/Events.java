package com.volleyhooks.volley;

import com.volleyhooks.volley.models.*;
import okhttp3.Request;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Events API methods.
 */
public class Events {
    private final VolleyClient client;
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    Events(VolleyClient client) {
        this.client = client;
    }

    /**
     * Options for listing events.
     */
    public static class ListOptions {
        private Long sourceId;
        private Long connectionId;
        private Long destinationId;
        private String status;
        private Instant startTime;
        private Instant endTime;
        private String search;
        private Integer limit;
        private Integer offset;

        public Long getSourceId() { return sourceId; }
        public void setSourceId(Long sourceId) { this.sourceId = sourceId; }

        public Long getConnectionId() { return connectionId; }
        public void setConnectionId(Long connectionId) { this.connectionId = connectionId; }

        public Long getDestinationId() { return destinationId; }
        public void setDestinationId(Long destinationId) { this.destinationId = destinationId; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public Instant getStartTime() { return startTime; }
        public void setStartTime(Instant startTime) { this.startTime = startTime; }

        public Instant getEndTime() { return endTime; }
        public void setEndTime(Instant endTime) { this.endTime = endTime; }

        public String getSearch() { return search; }
        public void setSearch(String search) { this.search = search; }

        public Integer getLimit() { return limit; }
        public void setLimit(Integer limit) { this.limit = limit; }

        public Integer getOffset() { return offset; }
        public void setOffset(Integer offset) { this.offset = offset; }
    }

    /**
     * Lists all events/requests for a project with optional filters.
     */
    public ListEventsResponse list(Long projectId, ListOptions options) throws VolleyException {
        Map<String, String> queryParams = new HashMap<>();
        if (options != null) {
            if (options.getSourceId() != null) {
                queryParams.put("source_id", String.valueOf(options.getSourceId()));
            }
            if (options.getConnectionId() != null) {
                queryParams.put("connection_id", String.valueOf(options.getConnectionId()));
            }
            if (options.getDestinationId() != null) {
                queryParams.put("destination_id", String.valueOf(options.getDestinationId()));
            }
            if (options.getStatus() != null) {
                queryParams.put("status", options.getStatus());
            }
            if (options.getStartTime() != null) {
                queryParams.put("start_time", ISO_FORMATTER.format(options.getStartTime()));
            }
            if (options.getEndTime() != null) {
                queryParams.put("end_time", ISO_FORMATTER.format(options.getEndTime()));
            }
            if (options.getSearch() != null) {
                queryParams.put("search", options.getSearch());
            }
            if (options.getLimit() != null) {
                queryParams.put("limit", String.valueOf(options.getLimit()));
            }
            if (options.getOffset() != null) {
                queryParams.put("offset", String.valueOf(options.getOffset()));
            }
        }

        Request request = client.buildRequestWithQuery("GET", "/api/projects/" + projectId + "/requests", queryParams).build();
        return client.executeRequest(request, ListEventsResponse.class);
    }

    /**
     * Gets detailed information about a specific event by its database ID.
     */
    public Event get(Long requestId) throws VolleyException {
        Request request = client.buildRequest("GET", "/api/requests/" + requestId, null).build();
        GetEventResponse response = client.executeRequest(request, GetEventResponse.class);
        return response != null ? response.getRequest() : null;
    }

    /**
     * Replays a failed event by its event_id.
     */
    public ReplayEventResponse replay(ReplayEventRequest request) throws VolleyException {
        Request httpRequest = client.buildRequest("POST", "/api/replay-event", request).build();
        return client.executeRequest(httpRequest, ReplayEventResponse.class);
    }
}


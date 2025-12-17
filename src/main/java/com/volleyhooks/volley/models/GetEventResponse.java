package com.volleyhooks.volley.models;

/**
 * Response from getting a single event.
 */
public class GetEventResponse {
    private Event request;

    public Event getRequest() {
        return request;
    }

    public void setRequest(Event request) {
        this.request = request;
    }
}


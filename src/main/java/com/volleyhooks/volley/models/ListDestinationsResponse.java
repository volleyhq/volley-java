package com.volleyhooks.volley.models;

import java.util.List;

/**
 * Response from listing destinations.
 */
public class ListDestinationsResponse {
    private List<Destination> destinations;

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }
}


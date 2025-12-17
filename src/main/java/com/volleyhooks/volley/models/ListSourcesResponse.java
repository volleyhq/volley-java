package com.volleyhooks.volley.models;

import java.util.List;

/**
 * Response from listing sources.
 */
public class ListSourcesResponse {
    private List<Source> sources;

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}


package com.volleyhooks.volley.models;

import java.util.List;

/**
 * Response from listing connections.
 */
public class ListConnectionsResponse {
    private List<Connection> connections;

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}


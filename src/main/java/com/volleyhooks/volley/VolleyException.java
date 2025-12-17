package com.volleyhooks.volley;

/**
 * Exception thrown when a Volley API request fails.
 */
public class VolleyException extends Exception {
    private final int statusCode;

    public VolleyException(String message) {
        super(message);
        this.statusCode = 0;
    }

    public VolleyException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 0;
    }

    public VolleyException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Gets the HTTP status code if available.
     *
     * @return The status code, or 0 if not available
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Checks if this is an authentication error (401).
     */
    public boolean isUnauthorized() {
        return statusCode == 401;
    }

    /**
     * Checks if this is a forbidden error (403).
     */
    public boolean isForbidden() {
        return statusCode == 403;
    }

    /**
     * Checks if this is a not found error (404).
     */
    public boolean isNotFound() {
        return statusCode == 404;
    }

    /**
     * Checks if this is a rate limit error (429).
     */
    public boolean isRateLimited() {
        return statusCode == 429;
    }
}


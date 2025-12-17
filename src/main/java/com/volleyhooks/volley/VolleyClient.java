package com.volleyhooks.volley;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.Instant;
import okhttp3.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Main client for interacting with the Volley API.
 */
public class VolleyClient {
    private static final String DEFAULT_BASE_URL = "https://api.volleyhooks.com";
    private static final long DEFAULT_TIMEOUT_SECONDS = 30;

    private final String baseUrl;
    private final String apiToken;
    private Long organizationId;
    private final OkHttpClient httpClient;
    private final Gson gson;

    // API resource clients
    public final Organizations organizations;
    public final Projects projects;
    public final Sources sources;
    public final Destinations destinations;
    public final Connections connections;
    public final Events events;
    public final DeliveryAttempts deliveryAttempts;
    public final Webhooks webhooks;

    private VolleyClient(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.apiToken = builder.apiToken;
        this.organizationId = builder.organizationId;
        this.httpClient = builder.httpClient != null ? builder.httpClient : new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();

        // Initialize API resource clients
        this.organizations = new Organizations(this);
        this.projects = new Projects(this);
        this.sources = new Sources(this);
        this.destinations = new Destinations(this);
        this.connections = new Connections(this);
        this.events = new Events(this);
        this.deliveryAttempts = new DeliveryAttempts(this);
        this.webhooks = new Webhooks(this);
    }

    /**
     * Creates a new VolleyClient with the specified API token.
     *
     * @param apiToken Your Volley API token
     * @return A Builder instance for configuring the client
     */
    public static Builder create(String apiToken) {
        return new Builder(apiToken);
    }

    /**
     * Builder for creating a VolleyClient with custom options.
     */
    public static class Builder {
        private final String apiToken;
        private String baseUrl = DEFAULT_BASE_URL;
        private Long organizationId;
        private OkHttpClient httpClient;

        public Builder(String apiToken) {
            this.apiToken = apiToken;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder organizationId(Long organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public VolleyClient build() {
            return new VolleyClient(this);
        }
    }

    /**
     * Sets the organization ID for subsequent requests.
     *
     * @param organizationId The organization ID
     */
    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * Clears the organization ID (uses default organization).
     */
    public void clearOrganizationId() {
        this.organizationId = null;
    }

    /**
     * Gets the current organization ID.
     *
     * @return The organization ID, or null if not set
     */
    public Long getOrganizationId() {
        return organizationId;
    }

    /**
     * Performs an HTTP request with authentication.
     * Package-private for use by resource classes.
     */
    <T> T executeRequest(okhttp3.Request request, Class<T> responseClass) throws VolleyException {
        try {
            Response response = httpClient.newCall(request).execute();
            return parseResponse(response, responseClass);
        } catch (IOException e) {
            throw new VolleyException("Request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Performs an HTTP request that returns void.
     */
    void executeRequest(okhttp3.Request request) throws VolleyException {
        try {
            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                parseError(response);
            }
            response.close();
        } catch (IOException e) {
            throw new VolleyException("Request failed: " + e.getMessage(), e);
        }
    }

    /**
     * Builds a request with authentication headers.
     * Package-private for use by resource classes.
     */
    okhttp3.Request.Builder buildRequest(String method, String path, Object body) {
        Request.Builder builder = new Request.Builder()
                .url(baseUrl + path)
                .addHeader("Authorization", "Bearer " + apiToken)
                .addHeader("Content-Type", "application/json");

        if (organizationId != null) {
            builder.addHeader("X-Organization-ID", String.valueOf(organizationId));
        }

        if (body != null && (method.equals("POST") || method.equals("PUT"))) {
            String jsonBody = gson.toJson(body);
            RequestBody requestBody = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
            builder.method(method, requestBody);
        } else {
            builder.method(method, null);
        }

        return builder;
    }

    /**
     * Builds a request with query parameters.
     * Package-private for use by resource classes.
     */
    okhttp3.Request.Builder buildRequestWithQuery(String method, String path, Map<String, String> queryParams) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + path).newBuilder();
        if (queryParams != null) {
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                if (entry.getValue() != null) {
                    urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
                }
            }
        }

        Request.Builder builder = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Authorization", "Bearer " + apiToken);

        if (organizationId != null) {
            builder.addHeader("X-Organization-ID", String.valueOf(organizationId));
        }

        builder.method(method, null);
        return builder;
    }

    /**
     * Parses the response JSON into the specified class.
     */
    private <T> T parseResponse(okhttp3.Response response, Class<T> responseClass) throws VolleyException {
        try {
            if (!response.isSuccessful()) {
                parseError(response);
                return null;
            }

            String body = response.body() != null ? response.body().string() : null;
            if (body == null || body.isEmpty()) {
                return null;
            }

            return gson.fromJson(body, responseClass);
        } catch (IOException e) {
            throw new VolleyException("Failed to parse response: " + e.getMessage(), e);
        } finally {
            response.close();
        }
    }

    /**
     * Parses an error response.
     */
    private void parseError(okhttp3.Response response) throws VolleyException {
        try {
            String body = response.body() != null ? response.body().string() : null;
            if (body != null && !body.isEmpty()) {
                ErrorResponse errorResponse = gson.fromJson(body, ErrorResponse.class);
                throw new VolleyException(
                        errorResponse.getError() != null ? errorResponse.getError() : "API error",
                        response.code()
                );
            } else {
                throw new VolleyException("API error: " + response.message(), response.code());
            }
        } catch (IOException e) {
            throw new VolleyException("API error: " + response.message(), response.code());
        }
    }

    /**
     * Gets the Gson instance (for testing).
     */
    Gson getGson() {
        return gson;
    }

    /**
     * Gets the base URL (for testing).
     */
    String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Gets the HTTP client (for testing).
     */
    OkHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Error response model.
     */
    private static class ErrorResponse {
        private String error;
        private String message;

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Type adapter for java.time.Instant.
     */
    private static class InstantTypeAdapter extends TypeAdapter<Instant> {
        @Override
        public void write(JsonWriter out, Instant value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value.toString());
            }
        }

        @Override
        public Instant read(JsonReader in) throws IOException {
            if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return Instant.parse(in.nextString());
        }
    }
}


package com.volleyhooks.volley;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * Webhooks API methods.
 */
public class Webhooks {
    private final VolleyClient client;
    private final Gson gson;

    Webhooks(VolleyClient client) {
        this.client = client;
        this.gson = client.getGson();
    }

    /**
     * Sends a webhook to a source.
     *
     * @param sourceId The ingestion ID of the source
     * @param payload The webhook payload
     * @return The event ID of the queued webhook
     */
    public String send(String sourceId, Map<String, Object> payload) throws VolleyException {
        try {
            String jsonBody = gson.toJson(payload);
            RequestBody requestBody = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));

            String url = client.getBaseUrl() + "/hook/" + sourceId;
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .addHeader("Content-Type", "application/json")
                    .build();

            okhttp3.Response response = client.getHttpClient().newCall(request).execute();
            if (!response.isSuccessful()) {
                response.close();
                throw new VolleyException("Webhook request failed: " + response.message(), response.code());
            }

            if (response.body() == null) {
                response.close();
                throw new VolleyException("Empty response from webhook endpoint");
            }

            String responseBody = response.body().string();
            response.close();

            // Parse response to get event_id
            Map<String, Object> result = gson.fromJson(responseBody, Map.class);
            Object eventId = result.get("event_id");
            return eventId != null ? eventId.toString() : null;
        } catch (IOException e) {
            throw new VolleyException("Failed to send webhook: " + e.getMessage(), e);
        }
    }
}


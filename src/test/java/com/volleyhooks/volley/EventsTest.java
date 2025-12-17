package com.volleyhooks.volley;

import com.google.gson.Gson;
import com.volleyhooks.volley.models.*;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventsTest {

    private MockWebServer mockWebServer;
    private VolleyClient client;
    private Gson gson;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        client = VolleyClient.create("test-token")
                .baseUrl(mockWebServer.url("/").toString().replaceAll("/$", ""))
                .build();
        gson = client.getGson();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testListEvents() throws VolleyException {
        ListEventsResponse response = new ListEventsResponse();
        Event event = new Event();
        event.setId(1L);
        event.setEventId("evt_abc123");
        event.setStatus("failed");
        response.setRequests(List.of(event));
        response.setTotal(1L);
        response.setLimit(50);
        response.setOffset(0);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(response))
                .addHeader("Content-Type", "application/json"));

        Events.ListOptions options = new Events.ListOptions();
        options.setStatus("failed");
        options.setLimit(50);

        ListEventsResponse result = client.events.list(1L, options);
        assertNotNull(result);
        assertEquals(1, result.getRequests().size());
        assertEquals("evt_abc123", result.getRequests().get(0).getEventId());
    }

    @Test
    void testGetEvent() throws VolleyException {
        GetEventResponse response = new GetEventResponse();
        Event event = new Event();
        event.setId(123L);
        event.setEventId("evt_abc123");
        event.setStatus("failed");
        response.setRequest(event);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(response))
                .addHeader("Content-Type", "application/json"));

        Event result = client.events.get(123L);
        assertNotNull(result);
        assertEquals("evt_abc123", result.getEventId());
    }

    @Test
    void testReplayEvent() throws VolleyException {
        ReplayEventResponse response = new ReplayEventResponse();
        response.setSuccess(true);
        response.setStatus("success");
        response.setStatusCode(200);
        response.setDurationMs(150L);
        response.setAttemptId(456L);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(response))
                .addHeader("Content-Type", "application/json"));

        ReplayEventResponse result = client.events.replay(
                new ReplayEventRequest("evt_abc123def456")
        );
        assertNotNull(result);
        assertTrue(result.getSuccess());
        assertEquals(200, result.getStatusCode());
    }
}


package com.volleyhooks.volley;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class VolleyClientTest {

    private MockWebServer mockWebServer;
    private VolleyClient client;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        client = VolleyClient.create("test-token")
                .baseUrl(mockWebServer.url("/").toString().replaceAll("/$", ""))
                .build();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testCreateClient() {
        VolleyClient client = VolleyClient.create("test-token").build();
        assertNotNull(client);
    }

    @Test
    void testSetOrganizationId() {
        client.setOrganizationId(123L);
        assertEquals(123L, client.getOrganizationId());
    }

    @Test
    void testClearOrganizationId() {
        client.setOrganizationId(123L);
        client.clearOrganizationId();
        assertNull(client.getOrganizationId());
    }

    @Test
    void testClientWithCustomBaseUrl() {
        VolleyClient customClient = VolleyClient.create("test-token")
                .baseUrl("https://api-staging.volleyhooks.com")
                .build();
        assertNotNull(customClient);
    }

    @Test
    void testClientWithOrganizationId() {
        VolleyClient orgClient = VolleyClient.create("test-token")
                .organizationId(456L)
                .build();
        assertEquals(456L, orgClient.getOrganizationId());
    }
}


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

class OrganizationsTest {

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
        gson = new Gson();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testListOrganizations() throws VolleyException {
        ListOrganizationsResponse response = new ListOrganizationsResponse();
        Organization org = new Organization();
        org.setId(1L);
        org.setName("Test Org");
        org.setSlug("test-org");
        org.setRole("owner");
        response.setOrganizations(List.of(org));

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(response))
                .addHeader("Content-Type", "application/json"));

        List<Organization> orgs = client.organizations.list();
        assertNotNull(orgs);
        assertEquals(1, orgs.size());
        assertEquals("Test Org", orgs.get(0).getName());
    }

    @Test
    void testGetOrganization() throws VolleyException {
        Organization org = new Organization();
        org.setId(123L);
        org.setName("Test Org");
        org.setSlug("test-org");
        org.setRole("owner");

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(org))
                .addHeader("Content-Type", "application/json"));

        Organization result = client.organizations.get(123L);
        assertNotNull(result);
        assertEquals(123L, result.getId());
        assertEquals("Test Org", result.getName());
    }

    @Test
    void testCreateOrganization() throws VolleyException {
        Organization org = new Organization();
        org.setId(2L);
        org.setName("New Organization");
        org.setSlug("new-organization");
        org.setRole("owner");

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(201)
                .setBody(gson.toJson(org))
                .addHeader("Content-Type", "application/json"));

        Organization result = client.organizations.create(
                new CreateOrganizationRequest("New Organization")
        );
        assertNotNull(result);
        assertEquals("New Organization", result.getName());
    }

    @Test
    void testListOrganizationsError() {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(401)
                .setBody("{\"error\": \"unauthorized\"}")
                .addHeader("Content-Type", "application/json"));

        assertThrows(VolleyException.class, () -> {
            client.organizations.list();
        });
    }
}


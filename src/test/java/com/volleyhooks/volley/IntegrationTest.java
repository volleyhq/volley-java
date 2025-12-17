package com.volleyhooks.volley;

import com.volleyhooks.volley.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests that make real API calls.
 * These tests are skipped unless VOLLEY_API_TOKEN is set.
 */
@EnabledIfEnvironmentVariable(named = "VOLLEY_API_TOKEN", matches = ".+")
class IntegrationTest {

    @Test
    void testListOrganizations() throws VolleyException {
        String apiToken = System.getenv("VOLLEY_API_TOKEN");
        VolleyClient client = VolleyClient.create(apiToken);

        List<Organization> orgs = client.organizations.list();
        assertNotNull(orgs);
        // May be empty for new accounts, which is OK
    }

    @Test
    void testGetOrganization() throws VolleyException {
        String apiToken = System.getenv("VOLLEY_API_TOKEN");
        VolleyClient client = VolleyClient.create(apiToken);

        List<Organization> orgs = client.organizations.list();
        if (orgs == null || orgs.isEmpty()) {
            return; // Skip if no organizations
        }

        Organization org = client.organizations.get(orgs.get(0).getId());
        assertNotNull(org);
        assertEquals(orgs.get(0).getId(), org.getId());
    }

    @Test
    void testListProjects() throws VolleyException {
        String apiToken = System.getenv("VOLLEY_API_TOKEN");
        VolleyClient client = VolleyClient.create(apiToken);

        List<Organization> orgs = client.organizations.list();
        if (orgs == null || orgs.isEmpty()) {
            return; // Skip if no organizations
        }

        client.setOrganizationId(orgs.get(0).getId());
        List<Project> projects = client.projects.list();
        assertNotNull(projects);
        // May be empty, which is OK
    }

    @Test
    void testListSources() throws VolleyException {
        String apiToken = System.getenv("VOLLEY_API_TOKEN");
        VolleyClient client = VolleyClient.create(apiToken);

        List<Organization> orgs = client.organizations.list();
        if (orgs == null || orgs.isEmpty()) {
            return;
        }

        client.setOrganizationId(orgs.get(0).getId());
        List<Project> projects = client.projects.list();
        if (projects == null || projects.isEmpty()) {
            return;
        }

        List<Source> sources = client.sources.list(projects.get(0).getId());
        assertNotNull(sources);
        // May be empty, which is OK
    }
}


package com.volleyhooks.volley.example;

import com.volleyhooks.volley.VolleyClient;
import com.volleyhooks.volley.VolleyException;
import com.volleyhooks.volley.Events;
import com.volleyhooks.volley.models.*;

import java.util.List;

/**
 * Example usage of the Volley Java SDK.
 */
public class Example {
    public static void main(String[] args) {
        // Get API token from environment variable
        String apiToken = System.getenv("VOLLEY_API_TOKEN");
        if (apiToken == null || apiToken.isEmpty()) {
            System.err.println("VOLLEY_API_TOKEN environment variable is required");
            System.exit(1);
        }

        // Create a client
        VolleyClient client = VolleyClient.create(apiToken).build();

        try {
            // Example 1: List organizations
            System.out.println("=== Listing Organizations ===");
            List<Organization> orgs = client.organizations.list();
            if (orgs == null || orgs.isEmpty()) {
                System.out.println("  No organizations found");
                return;
            }

            for (Organization org : orgs) {
                System.out.println("  - " + org.getName() + " (ID: " + org.getId() + ", Role: " + org.getRole() + ")");
            }

            // Example 2: Set organization context
            Long orgID = orgs.get(0).getId();
            client.setOrganizationId(orgID);
            System.out.println("\n=== Using Organization: " + orgs.get(0).getName() + " (ID: " + orgID + ") ===");

            // Example 3: List projects
            System.out.println("\n=== Listing Projects ===");
            List<Project> projects = client.projects.list();
            if (projects == null || projects.isEmpty()) {
                System.out.println("  No projects found");
                return;
            }

            for (Project project : projects) {
                System.out.print("  - " + project.getName() + " (ID: " + project.getId());
                if (Boolean.TRUE.equals(project.getIsDefault())) {
                    System.out.print(", Default");
                }
                System.out.println(")");
            }

            // Example 4: List sources for first project
            Long projectID = projects.get(0).getId();
            System.out.println("\n=== Listing Sources for Project: " + projects.get(0).getName() + " (ID: " + projectID + ") ===");
            List<Source> sources = client.sources.list(projectID);
            if (sources == null || sources.isEmpty()) {
                System.out.println("  No sources found");
            } else {
                for (Source source : sources) {
                    System.out.println("  - " + source.getSlug() + " (ID: " + source.getId() + ", Ingestion ID: " + source.getIngestionId() + ")");
                }
            }

            // Example 5: List events (if any)
            System.out.println("\n=== Listing Recent Events for Project: " + projects.get(0).getName() + " ===");
            Events.ListOptions options = new Events.ListOptions();
            options.setLimit(10);
            ListEventsResponse events = client.events.list(projectID, options);
            if (events != null && events.getRequests() != null) {
                System.out.println("Total events: " + events.getTotal());
                int count = 0;
                for (Event event : events.getRequests()) {
                    if (count >= 5) break; // Show only first 5
                    System.out.println("  - Event ID: " + event.getEventId() + ", Status: " + event.getStatus());
                    count++;
                }
            }

        } catch (VolleyException e) {
            System.err.println("Error: " + e.getMessage());
            if (e.getStatusCode() > 0) {
                System.err.println("Status Code: " + e.getStatusCode());
            }
            e.printStackTrace();
        }
    }
}


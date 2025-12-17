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

class ProjectsTest {

    private MockWebServer mockWebServer;
    private VolleyClient client;
    private Gson gson;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        client = VolleyClient.create("test-token")
                .baseUrl(mockWebServer.url("/").toString().replaceAll("/$", ""))
                .organizationId(123L)
                .build();
        gson = client.getGson();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testListProjects() throws VolleyException {
        ListProjectsResponse response = new ListProjectsResponse();
        Project project = new Project();
        project.setId(1L);
        project.setName("Test Project");
        project.setIsDefault(true);
        response.setProjects(List.of(project));

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(response))
                .addHeader("Content-Type", "application/json"));

        List<Project> projects = client.projects.list();
        assertNotNull(projects);
        assertEquals(1, projects.size());
        assertEquals("Test Project", projects.get(0).getName());
    }

    @Test
    void testCreateProject() throws VolleyException {
        CreateProjectResponse response = new CreateProjectResponse();
        Project project = new Project();
        project.setId(2L);
        project.setName("New Project");
        project.setIsDefault(false);
        response.setProject(project);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(201)
                .setBody(gson.toJson(response))
                .addHeader("Content-Type", "application/json"));

        Project result = client.projects.create(
                new CreateProjectRequest("New Project")
        );
        assertNotNull(result);
        assertEquals("New Project", result.getName());
    }

    @Test
    void testUpdateProject() throws VolleyException {
        CreateProjectResponse response = new CreateProjectResponse();
        Project project = new Project();
        project.setId(1L);
        project.setName("Updated Project Name");
        response.setProject(project);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(gson.toJson(response))
                .addHeader("Content-Type", "application/json"));

        Project result = client.projects.update(1L,
                new UpdateProjectRequest("Updated Project Name")
        );
        assertNotNull(result);
        assertEquals("Updated Project Name", result.getName());
    }

    @Test
    void testDeleteProject() throws VolleyException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200));

        assertDoesNotThrow(() -> {
            client.projects.delete(1L);
        });
    }
}


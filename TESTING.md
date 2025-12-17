# Testing Guide

This document explains how to run and write tests for the Volley Java SDK.

## Test Structure

The SDK includes two types of tests:

1. **Unit Tests** - Use mock HTTP servers (MockWebServer), no API credentials required
2. **Integration Tests** - Make real API calls, require API token

## Running Tests

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

### Unit Tests (No API Token Required)

Run all unit tests:

```bash
mvn test
```

Run with verbose output:

```bash
mvn test -X
```

Run specific test class:

```bash
mvn test -Dtest=VolleyClientTest
```

### Integration Tests (Requires API Token)

Integration tests make real API calls to test the SDK against the actual Volley API.

**Setup:**

1. Get your API token from [Volley Console](https://app.volleyhooks.com) → Settings → Account → API Token
2. Set the environment variable:

```bash
export VOLLEY_API_TOKEN="your-api-token"
```

**Run integration tests:**

```bash
mvn test -Dtest=IntegrationTest
```

**Note**: Integration tests are automatically skipped if `VOLLEY_API_TOKEN` is not set (using `@EnabledIfEnvironmentVariable`).

## Test Coverage

View test coverage:

```bash
mvn test jacoco:report
```

The coverage report will be generated in `target/site/jacoco/index.html`.

## Test Files

- `VolleyClientTest.java` - Client initialization and configuration tests
- `OrganizationsTest.java` - Organization API tests
- `ProjectsTest.java` - Project API tests
- `EventsTest.java` - Event and replay API tests
- `IntegrationTest.java` - Real API integration tests

## Writing New Tests

### Unit Test Example

```java
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class MyFeatureTest {
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
    void testMyFeature() throws VolleyException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"data\": \"test\"}")
                .addHeader("Content-Type", "application/json"));

        // Test your feature
        // ...
    }
}
```

### Integration Test Example

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@EnabledIfEnvironmentVariable(named = "VOLLEY_API_TOKEN", matches = ".+")
class MyIntegrationTest {
    @Test
    void testMyFeature() throws VolleyException {
        String apiToken = System.getenv("VOLLEY_API_TOKEN");
        VolleyClient client = VolleyClient.create(apiToken);
        
        // Test against real API
        // ...
    }
}
```

## Best Practices

1. **Always test error cases** - Test both success and failure scenarios
2. **Verify request details** - Check HTTP method, path, headers, body
3. **Use descriptive test names** - `testListOrganizations` is better than `test1`
4. **Clean up resources** - Use `@AfterEach` for cleanup
5. **Skip integration tests** - When API token is not available
6. **Use MockWebServer** - For unit tests to avoid real network calls

## Continuous Integration

For CI/CD pipelines:

```yaml
# Example GitHub Actions
- name: Run unit tests
  run: mvn test

- name: Run integration tests
  env:
    VOLLEY_API_TOKEN: ${{ secrets.VOLLEY_API_TOKEN }}
  run: mvn test -Dtest=IntegrationTest
```

## Troubleshooting

### Tests fail with "connection refused"
- Make sure MockWebServer is started before making requests
- Check that `mockWebServer.shutdown()` is called in `@AfterEach`

### Integration tests skipped
- Verify `VOLLEY_API_TOKEN` environment variable is set
- Check that the annotation `@EnabledIfEnvironmentVariable` is correct

### Test coverage is low
- Add tests for error cases
- Test edge cases and boundary conditions
- Test all public methods


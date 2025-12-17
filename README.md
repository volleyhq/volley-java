# Volley Java SDK

Official Java SDK for the Volley API. This SDK provides a convenient way to interact with the Volley webhook infrastructure API.

[Volley](https://volleyhooks.com) is a webhook infrastructure platform that provides reliable webhook delivery, rate limiting, retries, monitoring, and more.

## Resources

- **Documentation**: [https://docs.volleyhooks.com](https://docs.volleyhooks.com)
- **Getting Started Guide**: [https://docs.volleyhooks.com/getting-started](https://docs.volleyhooks.com/getting-started)
- **API Reference**: [https://docs.volleyhooks.com/api](https://docs.volleyhooks.com/api)
- **Authentication Guide**: [https://docs.volleyhooks.com/authentication](https://docs.volleyhooks.com/authentication)
- **Security Guide**: [https://docs.volleyhooks.com/security](https://docs.volleyhooks.com/security)
- **Console**: [https://app.volleyhooks.com](https://app.volleyhooks.com)
- **Website**: [https://volleyhooks.com](https://volleyhooks.com)

## Installation

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>com.volleyhooks</groupId>
    <artifactId>volley-java</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

Add the following to your `build.gradle`:

```gradle
dependencies {
    implementation 'com.volleyhooks:volley-java:1.0.0'
}
```

## Quick Start

```java
import com.volleyhooks.volley.VolleyClient;
import com.volleyhooks.volley.VolleyException;
import com.volleyhooks.volley.models.Organization;

public class Example {
    public static void main(String[] args) {
        // Create a client with your API token
        VolleyClient client = VolleyClient.create("your-api-token");
        
        // Optionally set organization context
        client.setOrganizationId(123L);
        
        // List organizations
        try {
            List<Organization> orgs = client.organizations.list();
            for (Organization org : orgs) {
                System.out.println("Organization: " + org.getName() + " (ID: " + org.getId() + ")");
            }
        } catch (VolleyException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

## Authentication

Volley uses API tokens for authentication. These are long-lived tokens designed for programmatic access.

### Getting Your API Token

1. Log in to the [Volley Console](https://app.volleyhooks.com)
2. Navigate to **Settings → Account → API Token**
3. Click **View Token** (you may need to verify your password)
4. Copy the token and store it securely

**Important**: API tokens are non-expiring and provide full access to your account. Keep them secure and rotate them if compromised. See the [Security Guide](https://docs.volleyhooks.com/security) for best practices.

```java
VolleyClient client = VolleyClient.create("your-api-token");
```

For more details on authentication, API tokens, and security, see the [Authentication Guide](https://docs.volleyhooks.com/authentication) and [Security Guide](https://docs.volleyhooks.com/security).

## Organization Context

When you have multiple organizations, you need to specify which organization context to use for API requests. The API verifies that resources (like projects) belong to the specified organization.

You can set the organization context in two ways:

```java
// Method 1: Set organization ID for all subsequent requests
client.setOrganizationId(123L);

// Method 2: Create client with organization ID
VolleyClient client = VolleyClient.create("your-api-token")
    .organizationId(123L)
    .build();

// Clear organization context (uses first accessible organization)
client.clearOrganizationId();
```

**Note**: If you don't set an organization ID, the API uses your first accessible organization by default. For more details, see the [API Reference - Organization Context](https://docs.volleyhooks.com/api#organization-context).

## Examples

### Organizations

```java
// List all organizations
List<Organization> orgs = client.organizations.list();

// Get current organization
Organization org = client.organizations.get(null); // null = use default

// Create organization
Organization newOrg = client.organizations.create(
    new CreateOrganizationRequest("My Organization")
);
```

### Projects

```java
// List projects
List<Project> projects = client.projects.list();

// Create project
Project project = client.projects.create(
    new CreateProjectRequest("My Project")
);

// Update project
Project updated = client.projects.update(projectId,
    new UpdateProjectRequest("Updated Name")
);

// Delete project
client.projects.delete(projectId);
```

### Sources

```java
// List sources in a project
List<Source> sources = client.sources.list(projectId);

// Create source
Source source = client.sources.create(projectId,
    new CreateSourceRequest("Stripe Webhooks", 10, "none")
);

// Get source details
Source source = client.sources.get(sourceId);

// Update source
Source updated = client.sources.update(sourceId,
    new CreateSourceRequest("Updated Source", 20, "none")
);
```

### Destinations

```java
// List destinations
List<Destination> destinations = client.destinations.list(projectId);

// Create destination
Destination dest = client.destinations.create(projectId,
    new CreateDestinationRequest("Production Endpoint", 
        "https://api.example.com/webhooks", 5)
);
```

### Connections

```java
// List connections
List<Connection> connections = client.projects.getConnections(projectId);

// Create connection
Connection conn = client.connections.create(projectId,
    new CreateConnectionRequest(sourceId, destId, "enabled", 5, 3)
);
```

### Events

```java
// List events with filters
Events.ListOptions options = new Events.ListOptions();
options.setStatus("failed");
options.setSourceId(sourceId);
options.setLimit(50);
options.setOffset(0);

ListEventsResponse events = client.events.list(projectId, options);

// Get event details
Event event = client.events.get(requestId);

// Replay failed event
ReplayEventResponse result = client.events.replay(
    new ReplayEventRequest("evt_abc123def456")
);
```

### Delivery Attempts

```java
// List delivery attempts
DeliveryAttempts.ListOptions options = new DeliveryAttempts.ListOptions();
options.setEventId("evt_abc123");
options.setStatus("failed");
options.setLimit(50);

ListDeliveryAttemptsResponse attempts = client.deliveryAttempts.list(projectId, options);
```

### Sending Webhooks

```java
// Send a webhook to a source
Map<String, Object> payload = new HashMap<>();
payload.put("event", "user.created");
Map<String, Object> data = new HashMap<>();
data.put("user_id", "123");
data.put("email", "user@example.com");
payload.put("data", data);

String eventId = client.webhooks.send("source_ingestion_id", payload);
```

## Error Handling

The SDK throws `VolleyException` for API errors:

```java
try {
    Organization org = client.organizations.get(orgId);
} catch (VolleyException e) {
    if (e.isUnauthorized()) {
        System.err.println("Authentication failed");
    } else if (e.isNotFound()) {
        System.err.println("Organization not found");
    } else {
        System.err.println("API Error: " + e.getMessage() + " (Status: " + e.getStatusCode() + ")");
    }
}
```

### Common HTTP Status Codes

- `200` - Success
- `201` - Created
- `202` - Accepted (webhook queued)
- `400` - Bad Request (validation error)
- `401` - Unauthorized (invalid or missing API token)
- `403` - Forbidden (insufficient permissions)
- `404` - Not Found
- `429` - Rate Limit Exceeded
- `500` - Internal Server Error

For more details on error responses, see the [API Reference - Response Codes](https://docs.volleyhooks.com/api#response-codes).

## Client Configuration

You can configure the client with various options:

```java
// Custom base URL (for testing)
VolleyClient client = VolleyClient.create("token")
    .baseUrl("https://api-staging.volleyhooks.com")
    .build();

// Custom HTTP client
OkHttpClient httpClient = new OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .build();

VolleyClient client = VolleyClient.create("token")
    .httpClient(httpClient)
    .build();
```

## Additional Resources

### Documentation

- **[Getting Started](https://docs.volleyhooks.com/getting-started)** - Set up your account and create your first webhook
- **[How It Works](https://docs.volleyhooks.com/how-it-works)** - Understand Volley's architecture
- **[Webhooks Guide](https://docs.volleyhooks.com/webhooks)** - Learn about webhook best practices and signature verification
- **[Rate Limiting](https://docs.volleyhooks.com/rate-limiting)** - Configure rate limits for your webhooks
- **[Monitoring](https://docs.volleyhooks.com/monitoring)** - Monitor webhook delivery and performance
- **[Best Practices](https://docs.volleyhooks.com/best-practices)** - Webhook development best practices
- **[FAQ](https://docs.volleyhooks.com/faq)** - Frequently asked questions

### Use Cases

- [Stripe Webhook Localhost Testing](https://docs.volleyhooks.com/use-cases/stripe-webhook-localhost)
- [Retrying Failed Webhooks](https://docs.volleyhooks.com/use-cases/retrying-failed-webhooks)
- [Webhook Event Replay](https://docs.volleyhooks.com/use-cases/webhook-event-replay)
- [Webhook Fan-out](https://docs.volleyhooks.com/use-cases/webhook-fan-out)
- [Multi-Tenant Webhooks](https://docs.volleyhooks.com/use-cases/multi-tenant-webhooks)

## Testing

The SDK includes comprehensive unit tests and integration tests.

### Running Unit Tests

Unit tests use a mock HTTP server and don't require API credentials:

```bash
mvn test
```

### Running Integration Tests

Integration tests make real API calls to the Volley API. You'll need to set your API token:

```bash
export VOLLEY_API_TOKEN="your-api-token"
mvn test -Dtest=*IntegrationTest
```

**Note**: Integration tests are skipped if `VOLLEY_API_TOKEN` is not set.

## Support

- **Documentation**: [https://docs.volleyhooks.com](https://docs.volleyhooks.com)
- **Console**: [https://app.volleyhooks.com](https://app.volleyhooks.com)
- **Website**: [https://volleyhooks.com](https://volleyhooks.com)

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

When contributing:
1. Add tests for new functionality
2. Ensure all tests pass (`mvn test`)
3. Update documentation as needed

## License

MIT License - see [LICENSE](LICENSE) file for details.


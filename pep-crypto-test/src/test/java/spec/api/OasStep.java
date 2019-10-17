package spec.api;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.apache.http.client.utils.URIUtils;
import spec.BasicStep;
import spec.TargetDefaultEndpointStep;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OasStep
        extends BasicStep {

    @Step("Endpoint contains OAS consisting out of POST operation with working examples for the following paths <table>")
    public void implementation1(Table table) throws IOException, InterruptedException {
        var openAPI = readOpenAPI();
        var paths = assertPaths(table, openAPI);

        var assertedOperations = paths.entrySet().stream()
                                      .map(this::assertPath)
                                      .collect(Collectors.toSet());

        assertEquals(table.getTableRows().size(), assertedOperations.size());

    }

    private String assertPath(Entry<String, PathItem> entry) {
        var example = "undefined";
        var responseBody = "";
        URI uri = null;

        try {
            var path = entry.getKey(); //path
            var pathItem = entry.getValue();
            var operations = pathItem.readOperations();

            assertEquals(1, operations.size());

            var operation = operations.iterator().next();

            var requestBody = operation.getRequestBody();
            var requestBodyContents = requestBody.getContent();

            assertEquals(1, requestBodyContents.size());

            var requestBodyContent = requestBodyContents.entrySet().iterator().next();

            var requestBodyContentKey = requestBodyContent.getKey(); // application json
            var requestBodyContentValue = requestBodyContent.getValue();
            example = requestBodyContentValue.getExample().toString();

            var client = HttpClient.newHttpClient();
            var baseUri = TargetDefaultEndpointStep.getBaseUri();
            uri = URI.create(baseUri + path);

            var request = HttpRequest.newBuilder()
                                     .uri(uri)
                                     .header("Content-Type", requestBodyContentKey)
                                     .POST(HttpRequest.BodyPublishers.ofString(example))
                                     .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, response.statusCode());

            responseBody = response.body();
        } catch (AssertionError error) {
            responseBody = error.toString();
            throw error;
        } catch (InterruptedException | IOException cause) {
            responseBody = cause.toString();
        } finally {

            // Request
            writeMessage("<table>"
                                 + "<thead>"
                                 + "<tr><th>Request</th><th>Value</th></tr>"
                                 + "</thead>"
                                 + "<tbody>"
                                 + "<tr><td>URI</td><td>" + uri + "</td></tr>"
                                 + "<tr><td>EXAMPLE</td><td>" + example + "</td></tr>"
                                 + "<tr><td>RESPONSE</td><td><pre>" + responseBody + "</pre></td></tr>"
                                 + "</tbody>"
                                 + "</table>"
            );
        }

        return responseBody;
    }

    private Paths assertPaths(Table table, OpenAPI openAPI) {
        // Expected paths
        var expectedPaths = table.getTableRows().stream()
                                 .map(it -> it.getCell("Path"))
                                 .collect(Collectors.toList());

        var paths = openAPI.getPaths();
        var actualPaths = paths.keySet();

        assertTrue(expectedPaths.containsAll(actualPaths));
        assertTrue(actualPaths.containsAll(expectedPaths));

        return openAPI.getPaths();
    }

    private OpenAPI readOpenAPI() throws IOException, InterruptedException {
        var specData = getSpecData(URI.class);
        var uri = URIUtils.resolve(specData, "/openapi");

        var request = HttpRequest.newBuilder()
                                 .uri(uri)
                                 .header("Content-Type", "application/yaml")
                                 .GET()
                                 .build();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var yamlString = response.body();
        var parserResults = new OpenAPIV3Parser().readContents(yamlString);

        assertTrue(parserResults.getMessages().isEmpty());

        return parserResults.getOpenAPI();
    }

}

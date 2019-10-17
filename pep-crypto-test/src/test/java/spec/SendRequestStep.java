package spec;

import client.ResponseHolder;
import com.jayway.jsonpath.JsonPath;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import data.SchemeKeys;
import groovy.json.JsonOutput;
import nl.logius.pepcrypto.lib.TestResources;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Map.Entry;

public class SendRequestStep
        extends BasicStep {

    @Step("Send payload <table>")
    public void sendPayload(Table table) throws IOException, InterruptedException {
        var specData = getSpecDataset();
        var tableRows = table.getTableRows();
        var requestData = new HashMap<String, Object>();

        for (var tableRow : tableRows) {
            var propertyNameColumnValue = tableRow.getCell("propertyName");
            var propertyValueColumnValue = tableRow.getCell("propertyValue");
            var propertyValueExpression = propertyValueColumnValue.replaceAll("^\\s*\\{\\{\\s*([\\S]*)\\s*$", "$1");
            var propertyFilterColumnValue = tableRow.getCell("propertyFilter");
            var propertyFilterExpression = propertyFilterColumnValue.replaceAll("^\\s*([\\S]*)\\s*\\}\\}\\s*$", "$1");

            // expresion
            var expressionParts = propertyValueExpression.split("\\|");
            var jsonPath = expressionParts[0];

            Object propertyValue;
            try {
                propertyValue = JsonPath.read(specData, jsonPath);
            } catch (RuntimeException cause) {
                propertyValue = null;
            }

            Object value = Void.TYPE;
            switch (propertyFilterExpression) {
                case "binary":
                    value = TestResources.resourceToByteArray((String) propertyValue);
                    break;
                case "string":
                    value = propertyValue;
                    break;
                case "schemeKeys":
                    value = Optional.ofNullable((Map<String, String>) propertyValue)
                                    .map(Map::entrySet)
                                    .orElse(Collections.emptySet()).stream()
                                    .map(SchemeKeys::fromResource)
                                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
                    break;
                case "serviceProviderKeys":
                    value = ((List) propertyValue).stream()
                                                  .map(it -> TestResources.resourceToString((String) it))
                                                  .collect(Collectors.toList());
                    break;
            }

            requestData.put(propertyNameColumnValue, value);
        }

        var uri = getSpecData(URI.class);
        var payload = (String) requestData.get("payload");

        // Request
        writeMessage("<table>"
                             + "<thead>"
                             + "<tr><th>Request</th><th>Value</th></tr>"
                             + "</thead>"
                             + "<tbody>"
                             + "<tr><td>URL</td><td>" + uri + "</td></tr>"
                             + "<tr><td>PAYLOAD</td><td><pre>" + payload + "</pre></td></tr>"
                             + "</tbody>"
                             + "</table>"
        );

        // Perform API call
        var request = HttpRequest.newBuilder()
                                 .uri(uri)
                                 .header("Content-Type", "application/json")
                                 .POST(HttpRequest.BodyPublishers.ofString(payload))
                                 .build();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        putScenarioData(HttpResponse.class, response);

        var responseHolder = new ResponseHolder(response);
        putScenarioData(ResponseHolder.class, responseHolder);

        var body = response.body();
        var statusCode = response.statusCode();

        String prettyJson;
        try {
            prettyJson = "<tr><td>BODY (JSON)</td><td><pre>" + JsonOutput.prettyPrint(body) + "</pre></td></tr>";
        } catch (RuntimeException cause) {
            prettyJson = "<tr><td>BODY (JSON)</td><td><pre>" + cause.getMessage() + "</pre></td></tr>";
        }

        String prettyYaml;
        try {
            prettyYaml = "<tr><td>BODY (YAML)</td><td><pre>" + prettyPrintAsYaml(body) + "</pre></td></tr>";
        } catch (RuntimeException cause) {
            prettyYaml = "<tr><td>BODY (YAML)</td><td><pre>" + cause.getMessage() + "</pre></td></tr>";
        }

        // Response

        writeMessage("<table>"
                             + "<thead>"
                             + "<tr><th>Response</th><th>Value</th></tr>"
                             + "</thead>"
                             + "<tbody>"
                             + "<tr><td>STATUS_CODE</td><td>" + statusCode + "</td></tr>"
                             + "<tr><td>BODY</td><td><pre>" + body + "<pre></td></tr>"
                             + prettyJson
                             + prettyYaml
                             + "</tbody>"
                             + "</table>"
        );
    }

}

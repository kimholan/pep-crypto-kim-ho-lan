package spec;

import client.Asserter;
import client.ResponseHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BasicStep {

    static {
        var defaults = newJsonPathConfigurationDefaults();
        Configuration.setDefaults(defaults);

    }

    private static Configuration.Defaults newJsonPathConfigurationDefaults() {
        return new Configuration.Defaults() {
            private final JsonProvider jsonProvider = new JacksonJsonProvider();

            private final MappingProvider mappingProvider = new JacksonMappingProvider();

            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }
        };
    }

    public static void writeMessage(String message) {
        System.err.println(message);
        Gauge.writeMessage(message);
    }

    public static String prettyPrintAsYaml(String jsonString) {
        // YAML pretty print
        var responseBody = (Map<String, Object>) new JsonSlurper().parseText(jsonString);
        var options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);
        options.setPrettyFlow(true);
        var yaml = new Yaml(options);
        return yaml.dump(responseBody);
    }

    protected static HashMap<Object, Object> sendJsonRequest(URI uri, Object object) throws IOException, InterruptedException {
        var exchange = new HashMap<>();
        var jsonRequest = new ObjectMapper().writeValueAsString(object);

        // Request
        writeMessage("<table>"
                             + "<thead>"
                             + "<tr><th>Request</th><th>Value</th></tr>"
                             + "</thead>"
                             + "<tbody>"
                             + "<tr><td>URL</td><td>" + uri + "</td></tr>"
                             + "<tr><td>PAYLOAD</td><td><pre>" + JsonOutput.prettyPrint(jsonRequest) + "</pre></td></tr>"
                             + "</tbody>"
                             + "</table>"
        );

        // Perform API call
        var request = HttpRequest.newBuilder()
                                 .uri(uri)
                                 .header("Content-Type", "application/json")
                                 .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                                 .build();
        var client = HttpClient.newHttpClient();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        exchange.put(HttpResponse.class, response);

        var responseHolder = new ResponseHolder(response);
        exchange.put(ResponseHolder.class, responseHolder);

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

        return exchange;
    }

    public Asserter getAsserter() {
        var scenarioDataStore = DataStoreFactory.getScenarioDataStore();
        var assertionResult = (Asserter) scenarioDataStore.get(Asserter.class);
        if (assertionResult == null) {
            assertionResult = new Asserter();
            scenarioDataStore.put(Asserter.class, assertionResult);
        }
        return assertionResult;
    }

    protected Map getScenarioDataMap() {
        var scenarioDataStore = DataStoreFactory.getScenarioDataStore();
        var map = (Map) scenarioDataStore.get(BasicStep.class);
        if (map == null) {
            map = new HashMap<>();
            scenarioDataStore.put(BasicStep.class, map);
        }
        return map;
    }

    protected <T> T getScenarioData(Class<T> key) {
        return (T) getScenarioDataMap().get(key);
    }

    protected <T> void putScenarioData(Class<T> type, T instance) {
        getScenarioDataMap().put(type, instance);
    }

    protected Map getSpecDataset() {
        var specDataStore = DataStoreFactory.getSpecDataStore();
        return (Map) specDataStore.get(BasicStep.class);
    }

    protected void setSpecDataset(Map map) {
        var specDataStore = DataStoreFactory.getSpecDataStore();
        specDataStore.put(BasicStep.class, map);
    }

    protected <T> void putSpecData(Class<T> type, T instance) {
        var specDataStore = DataStoreFactory.getSpecDataStore();
        specDataStore.put(type, instance);
    }

    protected <T> T getSpecData(Class<T> type) {
        var specDataStore = DataStoreFactory.getSpecDataStore();
        return (T) specDataStore.get(type);
    }

    protected void defaultSendScenarioRequest(Object requestObject) throws IOException, InterruptedException {
        var uri = getSpecData(URI.class);
        var exchange = sendJsonRequest(uri, requestObject);

        var response = (HttpResponse) exchange.get(HttpResponse.class);
        putScenarioData(HttpResponse.class, response);

        var responseHolder = (ResponseHolder) exchange.get(ResponseHolder.class);
        putScenarioData(ResponseHolder.class, responseHolder);
    }

}

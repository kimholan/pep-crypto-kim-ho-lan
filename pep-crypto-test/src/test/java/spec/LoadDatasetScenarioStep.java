package spec;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.AfterSpec;
import com.thoughtworks.gauge.BeforeSpec;
import com.thoughtworks.gauge.Step;
import groovy.lang.GroovyShell;
import nl.logius.pepcrypto.lib.TestResources;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.yaml.snakeyaml.DumperOptions.FlowStyle.BLOCK;

public class LoadDatasetScenarioStep
        extends BasicStep {

    private String resource;

    private List<Map<String, String>> patches;

    @Step("Load dataset from <resource>")
    public void loadDataSetFrom(String resource) {
        this.resource = resource;

        var data = TestResources.resourceToString(resource);

        var yaml = new Yaml();
        var map = (Map<?, ?>) yaml.load(data);

        setSpecDataset(map);

        if (Boolean.getBoolean("debug")) {
            writeMessage("<pre>" + data + "</pre>");
        }
    }

    @BeforeSpec
    public void beforeSpec() {
        patches = new ArrayList<>();
    }

    @AfterSpec
    public void afterSpec() throws URISyntaxException, IOException {
        if (!patches.isEmpty() && Optional.of("PC_PATCH_YAML").map(System::getenv).isPresent()) {
            try {
                var options = new DumperOptions();
                options.setDefaultFlowStyle(BLOCK);
                options.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);
                options.setPrettyFlow(true);
                var yaml = new Yaml(options);
                var data = TestResources.resourceToString(resource);
                var dataMap = yaml.load(data);

                var groovyShell = new GroovyShell();
                groovyShell.setVariable("__", dataMap);

                patches.stream()
                       .map(Map::entrySet)
                       .flatMap(Collection::stream)
                       .forEach(it -> groovyShell.evaluate("__." + it.getKey() + "=  new groovy.json.JsonSlurper().parseText('''" + it.getValue() + "''')"));

                var resource = TestResources.class.getResource(this.resource);
                var resourceFile = new File(resource.toURI());
                var patchedResourceFile = new File(resourceFile.getCanonicalFile().toString().replaceAll("/target/classes", "/specs"));

                try (var writer = new FileWriter(patchedResourceFile)) {
                    yaml.dump(dataMap, writer);
                    writer.flush();
                }

                writeMessage("Patched spec data: " + patchedResourceFile.getCanonicalFile());
            } catch (Exception exception) {
                throw new Error(exception);
            }
        }
    }

    @AfterScenario
    public void afterScenario() {
        var asserter = getAsserter();
        var scenarioPatches = asserter.getPatches();

        patches.addAll(scenarioPatches);
    }

}

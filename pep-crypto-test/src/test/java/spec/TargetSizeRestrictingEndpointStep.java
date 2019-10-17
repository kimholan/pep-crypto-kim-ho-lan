package spec;

import com.thoughtworks.gauge.Step;

import java.net.URI;
import java.util.Optional;

public class TargetSizeRestrictingEndpointStep
        extends BasicStep {

    @Step("Target size restricting endpoint <endpoint>")
    public void targetSizeRestrictingEndpoint(String endpoint) {
        var url = Optional.of("API_URL_MAX_POST_SIZE").map(System::getenv).orElse("http://localhost:8080/pep-crypto/api/v1");
        url += endpoint;
        // Perform API call
        var uri = URI.create(url);
        putSpecData(URI.class, uri);
        writeMessage("<table>" + "<tbody>" + "<tr><td>URI</td><td>" + uri + "</td></tr>" + "</tbody>" + "</table>");
    }

}

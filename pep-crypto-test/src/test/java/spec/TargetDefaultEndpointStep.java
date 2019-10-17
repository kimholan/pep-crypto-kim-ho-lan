package spec;

import com.thoughtworks.gauge.Step;

import java.net.URI;
import java.util.Optional;

public class TargetDefaultEndpointStep
        extends BasicStep {

    public static URI getBaseUri() {
        var baseUriString = Optional.of("API_URL_DEFAULT").map(System::getenv).orElse("http://localhost:8080/pep-crypto/api/v1");
        return URI.create(baseUriString);
    }

    public static URI newEndpointUri(String endpoint) {
        var baseUri = getBaseUri();

        return URI.create(baseUri + endpoint);
    }

    @Step("Target default endpoint <endpoint>")
    public void targetDefaultEndpoint(String endpoint) {
        var uri = newEndpointUri(endpoint);
        putSpecData(URI.class, uri);

        writeMessage("<table>" + "<tbody>" + "<tr><td>URI</td><td>" + uri + "</td></tr>" + "</tbody>" + "</table>");

    }

}

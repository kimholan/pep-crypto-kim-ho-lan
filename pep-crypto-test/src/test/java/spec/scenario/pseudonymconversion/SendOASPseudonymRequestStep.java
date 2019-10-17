package spec.scenario.pseudonymconversion;

import com.thoughtworks.gauge.Step;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymRequest;
import spec.BasicStep;

import java.io.IOException;

public class SendOASPseudonymRequestStep
        extends BasicStep {

    @Step("Send OASPseudonymRequest")
    public void sendPseudonymRequest() throws IOException, InterruptedException {
        var oasPseudonymRequest = getScenarioData(OASPseudonymRequest.class);

        defaultSendScenarioRequest(oasPseudonymRequest);
    }

}

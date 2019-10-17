package spec.scenario.signeddirectencryptedpseudonym;

import com.thoughtworks.gauge.Step;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymRequest;
import spec.BasicStep;

import java.io.IOException;

public class SendOASSignedDirectEncryptedPseudonymRequestStep
        extends BasicStep {

    @Step("Send OASSignedDirectEncryptedPseudonymRequest")
    public void sendOASSignedDirectEncryptedPseudonymRequest() throws IOException, InterruptedException {
        var oasSignedDirectEncryptedPseudonymRequest = getScenarioData(OASSignedDirectEncryptedPseudonymRequest.class);

        defaultSendScenarioRequest(oasSignedDirectEncryptedPseudonymRequest);
    }

}

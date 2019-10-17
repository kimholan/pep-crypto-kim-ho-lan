package spec.scenario.signedencryptedpseudonym;

import com.thoughtworks.gauge.Step;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymRequest;
import spec.BasicStep;

import java.io.IOException;

public class SendOASSignedEncryptedPseudonymRequestStep
        extends BasicStep {

    @Step("Send OASSignedEncryptedPseudonymRequest")
    public void sendOASSignedEncryptedPseudonymRequest() throws IOException, InterruptedException {
        var oasSignedEncryptedPseudonymRequest = getScenarioData(OASSignedEncryptedPseudonymRequest.class);

        defaultSendScenarioRequest(oasSignedEncryptedPseudonymRequest);
    }

}

package spec.scenario.signedencryptedidentity;

import com.thoughtworks.gauge.Step;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedIdentityRequest;
import spec.BasicStep;

import java.io.IOException;

public class SendOASSignedEncryptedIdentityRequestStep
        extends BasicStep {

    @Step("Send OASSignedEncryptedIdentityRequest")
    public void sendOASSignedEncryptedIdentityRequest() throws IOException, InterruptedException {
        var oasSignedEncryptedIdentityRequest = getScenarioData(OASSignedEncryptedIdentityRequest.class);

        defaultSendScenarioRequest(oasSignedEncryptedIdentityRequest);
    }

}

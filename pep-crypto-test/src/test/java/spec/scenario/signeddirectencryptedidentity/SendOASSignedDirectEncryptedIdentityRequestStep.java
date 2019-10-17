package spec.scenario.signeddirectencryptedidentity;

import com.thoughtworks.gauge.Step;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedIdentityRequest;
import spec.BasicStep;

import java.io.IOException;

public class SendOASSignedDirectEncryptedIdentityRequestStep
        extends BasicStep {

    @Step("Send OASSignedDirectEncryptedIdentityRequest")
    public void sendOASSignedDirectEncryptedIdentityRequest() throws IOException, InterruptedException {
        var oasSignedEncryptedIdentityRequest = getScenarioData(OASSignedDirectEncryptedIdentityRequest.class);

        defaultSendScenarioRequest(oasSignedEncryptedIdentityRequest);
    }

}

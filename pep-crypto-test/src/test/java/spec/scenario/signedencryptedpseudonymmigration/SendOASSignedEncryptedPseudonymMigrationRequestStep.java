package spec.scenario.signedencryptedpseudonymmigration;

import com.thoughtworks.gauge.Step;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymMigrationRequest;
import spec.BasicStep;

import java.io.IOException;

public class SendOASSignedEncryptedPseudonymMigrationRequestStep
        extends BasicStep {

    @Step("Send OASSignedEncryptedPseudonymMigrationRequest")
    public void sendOASSignedEncryptedPseudonymMigrationRequest() throws IOException, InterruptedException {
        var oasSignedEncryptedPseudonymMigrationRequest = getScenarioData(OASSignedEncryptedPseudonymMigrationRequest.class);

        defaultSendScenarioRequest(oasSignedEncryptedPseudonymMigrationRequest);
    }

}

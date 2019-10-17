package spec.scenario.signeddirectencryptedpseudonymmigration;

import com.thoughtworks.gauge.Step;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymMigrationRequest;
import spec.BasicStep;

import java.io.IOException;

public class SendOASSignedDirectEncryptedPseudonymMigrationRequestStep
        extends BasicStep {

    @Step("Send OASSignedDirectEncryptedPseudonymMigrationRequest")
    public void sendOASSignedDirectEncryptedPseudonymMigrationRequest() throws IOException, InterruptedException {
        var oasSignedDirectEncryptedPseudonymMigrationRequest = getScenarioData(OASSignedDirectEncryptedPseudonymMigrationRequest.class);

        defaultSendScenarioRequest(oasSignedDirectEncryptedPseudonymMigrationRequest);
    }

}

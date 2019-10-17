package spec.scenario.pseudonymmigrationimport;

import com.thoughtworks.gauge.Step;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationImportRequest;
import spec.BasicStep;

import java.io.IOException;

public class SendOASPseudonymMigrationImportRequestStep
        extends BasicStep {

    @Step("Send OASPseudonymMigrationImportRequest")
    public void sendPseudonymRequest() throws IOException, InterruptedException {
        var oasPseudonymMigrationImportRequest = getScenarioData(OASPseudonymMigrationImportRequest.class);

        defaultSendScenarioRequest(oasPseudonymMigrationImportRequest);
    }

}

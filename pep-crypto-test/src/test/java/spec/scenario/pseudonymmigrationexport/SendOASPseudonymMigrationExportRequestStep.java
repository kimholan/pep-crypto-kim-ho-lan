package spec.scenario.pseudonymmigrationexport;

import com.thoughtworks.gauge.Step;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationExportRequest;
import spec.BasicStep;

import java.io.IOException;

public class SendOASPseudonymMigrationExportRequestStep
        extends BasicStep {

    @Step("Send OASPseudonymMigrationExportRequest")
    public void sendPseudonymRequest() throws IOException, InterruptedException {
        var oasPseudonymMigrationExportRequest = getScenarioData(OASPseudonymMigrationExportRequest.class);

        defaultSendScenarioRequest(oasPseudonymMigrationExportRequest);
    }

}

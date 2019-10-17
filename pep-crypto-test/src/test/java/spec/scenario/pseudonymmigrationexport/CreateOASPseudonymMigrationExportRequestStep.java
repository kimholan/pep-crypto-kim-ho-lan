package spec.scenario.pseudonymmigrationexport;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationExportRequest;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.util.List;

public class CreateOASPseudonymMigrationExportRequestStep
        extends BasicStep {

    @Step("Create OASPseudonymMigrationExportRequest <table>")
    public void createRequest(Table propertyTable) {
        var specData = getSpecDataset();
        var requestData = SpecDataPropertyTable.asMap(specData, propertyTable);
        var request = new OASPseudonymMigrationExportRequest();

        request.setPseudonym((byte[]) requestData.get("pseudonym"));
        request.setMigrationID((String) requestData.get("migrationID"));
        request.setServiceProviderKeys((List<String>) requestData.get("serviceProviderKeys"));
        request.setTargetMigrant((String) requestData.get("targetMigrant"));
        request.setTargetMigrantKeySetVersion((String) requestData.get("targetMigrantKeySetVersion"));

        putScenarioData(OASPseudonymMigrationExportRequest.class, request);
    }

}

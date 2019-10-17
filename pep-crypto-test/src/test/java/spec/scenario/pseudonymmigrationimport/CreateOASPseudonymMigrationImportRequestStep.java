package spec.scenario.pseudonymmigrationimport;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationImportRequest;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.util.List;

public class CreateOASPseudonymMigrationImportRequestStep
        extends BasicStep {

    @Step("Create OASPseudonymMigrationImportRequest <table>")
    public void createRequest(Table propertyTable) {
        var specData = getSpecDataset();
        var requestData = SpecDataPropertyTable.asMap(specData, propertyTable);
        var request = new OASPseudonymMigrationImportRequest();

        request.setMigrationIntermediaryPseudonym((byte[]) requestData.get("migrationIntermediaryPseudonym"));
        request.setServiceProviderKeys((List<String>) requestData.get("serviceProviderKeys"));

        putScenarioData(OASPseudonymMigrationImportRequest.class, request);
    }

}

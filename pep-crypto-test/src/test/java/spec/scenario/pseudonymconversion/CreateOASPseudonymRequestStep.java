package spec.scenario.pseudonymconversion;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymRequest;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.util.List;

public class CreateOASPseudonymRequestStep
        extends BasicStep {

    @Step("Create OASPseudonymRequest <table>")
    public void createRequest(Table propertyTable) {
        var specData = getSpecDataset();
        var requestData = SpecDataPropertyTable.asMap(specData, propertyTable);
        var request = new OASPseudonymRequest();

        request.setPseudonym((byte[]) requestData.get("pseudonym"));
        request.setTargetClosingKey((String) requestData.get("targetClosingKey"));
        request.setServiceProviderKeys((List<String>) requestData.get("serviceProviderKeys"));

        putScenarioData(OASPseudonymRequest.class, request);
    }

}

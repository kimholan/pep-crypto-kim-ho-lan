package spec.scenario.signedencryptedpseudonym;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymRequest;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.util.List;
import java.util.Map;

public class CreateOASSignedEncryptedPseudonymRequestStep
        extends BasicStep {

    @Step("Create OASSignedEncryptedPseudonymRequest <table>")
    public void createRequest(Table propertyTable) {
        var specData = getSpecDataset();
        var requestData = SpecDataPropertyTable.asMap(specData, propertyTable);
        var request = new OASSignedEncryptedPseudonymRequest();

        request.setSignedEncryptedPseudonym((byte[]) requestData.get("signedEncryptedPseudonym"));
        request.setTargetClosingKey((String) requestData.get("targetClosingKey"));
        request.setSchemeKeys((Map<String, byte[]>) requestData.get("schemeKeys"));
        request.setServiceProviderKeys((List<String>) requestData.get("serviceProviderKeys"));

        putScenarioData(OASSignedEncryptedPseudonymRequest.class, request);
    }

}

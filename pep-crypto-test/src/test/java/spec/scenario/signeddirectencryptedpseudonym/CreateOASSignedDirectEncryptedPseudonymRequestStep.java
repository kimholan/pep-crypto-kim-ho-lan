package spec.scenario.signeddirectencryptedpseudonym;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymRequest;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.util.List;
import java.util.Map;

public class CreateOASSignedDirectEncryptedPseudonymRequestStep
        extends BasicStep {

    @Step("Create OASSignedDirectEncryptedPseudonymRequest <table>")
    public void createRequest(Table propertyTable) {
        var specData = getSpecDataset();
        var requestData = SpecDataPropertyTable.asMap(specData, propertyTable);
        var request = new OASSignedDirectEncryptedPseudonymRequest();

        request.setSignedDirectEncryptedPseudonym((byte[]) requestData.get("signedDirectEncryptedPseudonym"));
        request.setTargetClosingKey((String) requestData.get("targetClosingKey"));
        request.setSchemeKeys((Map<String, byte[]>) requestData.get("schemeKeys"));
        request.setServiceProviderKeys((List<String>) requestData.get("serviceProviderKeys"));
        request.setAuthorizedParty((String) requestData.get("authorizedParty"));

        putScenarioData(OASSignedDirectEncryptedPseudonymRequest.class, request);
    }

}

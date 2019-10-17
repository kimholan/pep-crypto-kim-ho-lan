package spec.scenario.signeddirectencryptedidentity;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedIdentityRequest;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.util.List;
import java.util.Map;

public class CreateOASSignedDirectEncryptedIdentityRequestStep
        extends BasicStep {

    @Step("Create OASSignedDirectEncryptedIdentityRequest <table>")
    public void createRequest(Table propertyTable) {
        var specData = getSpecDataset();
        var requestData = SpecDataPropertyTable.asMap(specData, propertyTable);
        var request = new OASSignedDirectEncryptedIdentityRequest();

        request.setSignedDirectEncryptedIdentity((byte[]) requestData.get("signedDirectEncryptedIdentity"));
        request.setAuthorizedParty((String) requestData.get("authorizedParty"));
        request.setSchemeKeys((Map<String, byte[]>) requestData.get("schemeKeys"));
        request.setServiceProviderKeys((List<String>) requestData.get("serviceProviderKeys"));

        putScenarioData(OASSignedDirectEncryptedIdentityRequest.class, request);
    }

}

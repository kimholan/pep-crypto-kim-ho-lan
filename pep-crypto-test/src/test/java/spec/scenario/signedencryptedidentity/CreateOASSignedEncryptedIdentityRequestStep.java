package spec.scenario.signedencryptedidentity;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedIdentityRequest;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.util.List;
import java.util.Map;

public class CreateOASSignedEncryptedIdentityRequestStep
        extends BasicStep {

    @Step("Create OASSignedEncryptedIdentityRequest <table>")
    public void createRequest(Table propertyTable) {
        var specData = getSpecDataset();
        var requestData = SpecDataPropertyTable.asMap(specData, propertyTable);
        var request = new OASSignedEncryptedIdentityRequest();

        request.setSignedEncryptedIdentity((byte[]) requestData.get("signedEncryptedIdentity"));
        request.setSchemeKeys((Map<String, byte[]>) requestData.get("schemeKeys"));
        request.setServiceProviderKeys((List<String>) requestData.get("serviceProviderKeys"));

        putScenarioData(OASSignedEncryptedIdentityRequest.class, request);
    }

}

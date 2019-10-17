package spec.scenario.signeddirectencryptedidentity;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedIdentityRequest;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.util.List;
import java.util.Map;

public class TemplateOASSignedDirectEncryptedIdentityRequestStep
        extends BasicStep {

    private Map<String, Object> requestData;

    @Step("Template OASSignedDirectEncryptedIdentityRequest <table>")
    public void templateOasSignedDirectEncryptedIdentityRequest(Table propertyTable) {
        putScenarioData(TemplateOASSignedDirectEncryptedIdentityRequestStep.class, this);

        var specData = getSpecDataset();
        requestData = SpecDataPropertyTable.asMap(specData, propertyTable);
    }

    public OASSignedDirectEncryptedIdentityRequest newOasSignedDirectEncryptedIdentityRequestStepFromTemplate() {
        var request = new OASSignedDirectEncryptedIdentityRequest();

        request.setSignedDirectEncryptedIdentity((byte[]) requestData.get("signedDirectEncryptedIdentity"));
        request.setAuthorizedParty((String) requestData.get("authorizedParty"));
        request.setSchemeKeys((Map<String, byte[]>) requestData.get("schemeKeys"));
        request.setServiceProviderKeys((List<String>) requestData.get("serviceProviderKeys"));

        return request;
    }

}

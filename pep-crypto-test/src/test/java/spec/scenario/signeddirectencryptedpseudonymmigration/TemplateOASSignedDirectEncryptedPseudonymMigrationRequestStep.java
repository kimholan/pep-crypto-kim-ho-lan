package spec.scenario.signeddirectencryptedpseudonymmigration;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymMigrationRequest;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.util.List;
import java.util.Map;

public class TemplateOASSignedDirectEncryptedPseudonymMigrationRequestStep
        extends BasicStep {

    private Map<String, Object> requestData;

    @Step("Template OASSignedDirectEncryptedPseudonymMigrationRequest <table>")
    public void createRequest(Table propertyTable) {
        putScenarioData(TemplateOASSignedDirectEncryptedPseudonymMigrationRequestStep.class, this);

        var specData = getSpecDataset();
        requestData = SpecDataPropertyTable.asMap(specData, propertyTable);
    }

    public OASSignedDirectEncryptedPseudonymMigrationRequest newOasSignedDirectEncryptedPseudonymMigrationRequestFromTemplate() {
        var request = new OASSignedDirectEncryptedPseudonymMigrationRequest();

        request.setSignedDirectEncryptedPseudonym((byte[]) requestData.get("signedDirectEncryptedPseudonym"));
        request.setServiceProviderKeys((List<String>) requestData.get("serviceProviderKeys"));
        request.setSchemeKeys((Map<String, byte[]>) requestData.get("schemeKeys"));
        request.setAuthorizedParty((String) requestData.get("authorizedParty"));
        request.setMigrationID((String) requestData.get("migrationID"));
        request.setTargetMigrant((String) requestData.get("targetMigrant"));
        request.setTargetMigrantKeySetVersion((String) requestData.get("targetMigrantKeySetVersion"));

        return request;
    }

}

package spec.scenario.signedencryptedpseudonymmigration;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymMigrationRequest;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.util.List;
import java.util.Map;

public class TemplateOASSignedEncryptedPseudonymMigrationRequestStep
        extends BasicStep {

    private Map<String, Object> requestData;

    @Step("Template OASSignedEncryptedPseudonymMigrationRequest <table>")
    public void createRequest(Table propertyTable) {
        putScenarioData(TemplateOASSignedEncryptedPseudonymMigrationRequestStep.class, this);

        var specData = getSpecDataset();
        requestData = SpecDataPropertyTable.asMap(specData, propertyTable);
    }

    public OASSignedEncryptedPseudonymMigrationRequest newOasSignedEncryptedPseudonymMigrationRequestFromTemplate() {
        var request = new OASSignedEncryptedPseudonymMigrationRequest();

        request.setSignedEncryptedPseudonym((byte[]) requestData.get("signedEncryptedPseudonym"));
        request.setServiceProviderKeys((List<String>) requestData.get("serviceProviderKeys"));
        request.setSchemeKeys((Map<String, byte[]>) requestData.get("schemeKeys"));
        request.setMigrationID((String) requestData.get("migrationID"));
        request.setTargetMigrant((String) requestData.get("targetMigrant"));
        request.setTargetMigrantKeySetVersion((String) requestData.get("targetMigrantKeySetVersion"));

        return request;
    }

}

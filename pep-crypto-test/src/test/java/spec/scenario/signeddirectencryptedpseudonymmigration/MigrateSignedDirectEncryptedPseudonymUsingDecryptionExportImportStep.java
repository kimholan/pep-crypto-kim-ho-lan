package spec.scenario.signeddirectencryptedpseudonymmigration;

import client.ResponseHolder;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationExportRequest;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationImportRequest;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymRequest;
import groovy.json.JsonOutput;
import org.apache.commons.codec.binary.Base64;
import spec.BasicStep;
import spec.SpecDataAssertionTable;
import spec.SpecPropertiesStep;
import spec.TargetDefaultEndpointStep;

import java.io.IOException;
import java.util.Map;

public class MigrateSignedDirectEncryptedPseudonymUsingDecryptionExportImportStep
        extends BasicStep {

    @Step("Migrate SignedDirectEncryptedPseudonym using OASSignedDirectEncryptedPseudonymRequest, OASPseudonymMigrationExportRequest and OASPseudonymMigrationImportRequest <table>")
    public void migrate(Table assertionTable) throws IOException, InterruptedException {
        var specProperties = getSpecData(SpecPropertiesStep.class);

        // SDEP
        var sdepEndpoint = specProperties.get(OASSignedDirectEncryptedPseudonymRequest.class.getSimpleName());
        var sdepEndpointUri = TargetDefaultEndpointStep.newEndpointUri(sdepEndpoint);
        var sdepRequest = getScenarioData(OASSignedDirectEncryptedPseudonymRequest.class);
        var sdepExchange = sendJsonRequest(sdepEndpointUri, sdepRequest);

        // PME
        var sourcePseudonymResponseHolder = (ResponseHolder) sdepExchange.get(ResponseHolder.class);
        var sourcePseudonymResponseJson = (Map) sourcePseudonymResponseHolder.get("json");
        var sourcePseudonymBase64 = (String) sourcePseudonymResponseJson.get("pseudonym");
        var sourcePseudonym = Base64.decodeBase64(sourcePseudonymBase64);

        var pmeEndpoint = specProperties.get(OASPseudonymMigrationExportRequest.class.getSimpleName());
        var pmeEndpointUri = TargetDefaultEndpointStep.newEndpointUri(pmeEndpoint);
        var pmeRequest = getScenarioData(OASPseudonymMigrationExportRequest.class);
        pmeRequest.setPseudonym(sourcePseudonym);
        var pmeExchange = sendJsonRequest(pmeEndpointUri, pmeRequest);

        // PMI
        var migrationIntermediaryPseudonymResponseHolder = (ResponseHolder) pmeExchange.get(ResponseHolder.class);
        var migrationIntermediaryPseudonymResponseJson = (Map) migrationIntermediaryPseudonymResponseHolder.get("json");
        var migrationIntermediaryPseudonymBase64 = (String) migrationIntermediaryPseudonymResponseJson.get("migrationIntermediaryPseudonym");
        var migrationIntermediaryPseudonym = Base64.decodeBase64(migrationIntermediaryPseudonymBase64);

        var pmiEndpoint = specProperties.get(OASPseudonymMigrationImportRequest.class.getSimpleName());
        var pmiEndpointUri = TargetDefaultEndpointStep.newEndpointUri(pmiEndpoint);
        var pmiRequest = getScenarioData(OASPseudonymMigrationImportRequest.class);
        pmiRequest.setMigrationIntermediaryPseudonym(migrationIntermediaryPseudonym);
        var pmiExchange = sendJsonRequest(pmiEndpointUri, pmiRequest);

        // Actual values
        var targetPseudonymResponseHolder = (ResponseHolder) pmiExchange.get(ResponseHolder.class);
        var targetPseudonymResponseJson = (Map) targetPseudonymResponseHolder.get("json");
        var targetPseudonymBase64 = (String) targetPseudonymResponseJson.get("pseudonym");

        // Assertions
        var actualValuesJson = JsonOutput.toJson(Map.of(
                "sourcePseudonym", sourcePseudonymBase64,
                "targetPseudonym", targetPseudonymBase64,
                "migrationIntermediaryPseudonym", migrationIntermediaryPseudonymBase64
        ));

        var expectedValuesDataSource = getSpecDataset();
        var asserter = getAsserter();

        SpecDataAssertionTable.evaluate(expectedValuesDataSource, actualValuesJson, assertionTable, asserter);
    }

}

package spec.scenario.signedencryptedpseudonymmigration;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import spec.BasicStep;
import spec.SpecDataPropertyTable;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SendTemplateOasSignedEncryptedPseudonymMigrationRequestStep
        extends BasicStep {

    @Step("Send template OASSignedEncryptedPseudonymMigrationRequest using values and assertions <table>")
    public void sendOASSignedEncryptedPseudonymMigrationRequest(Table propertyTable) throws IOException, InterruptedException {
        for (var propertyTableRow : propertyTable.getTableRows()) {
            var exchange = sendOASSignedEncryptedPseudonymMigrationRequestForTableRow(propertyTableRow);
            assertResponse(propertyTableRow, exchange);
        }
    }

    private void assertResponse(TableRow propertyTableRow, HashMap<Object, Object> exchange) {
        var asserter = getAsserter();

        // Status
        var response = (HttpResponse) exchange.get(HttpResponse.class);
        var statusCode = String.valueOf(response.statusCode());
        var expectedStatus = propertyTableRow.getCell("status");
        asserter.addAssertTrue("\nExpect status '" + statusCode + "' does not match HTTP status.\n\tExpected: " + propertyTableRow + "\n\tActual :" + statusCode, statusCode.equals(expectedStatus));

        // Token in body
        var expectedTokenInBody = propertyTableRow.getCell("token");
        var body = (String) response.body();
        asserter.addAssertTrue("\nExpected token '" + expectedTokenInBody + "' not found in body.\n\tExpected: " + propertyTableRow + "\n\tActual :" + body, body.contains(expectedTokenInBody));
    }

    private HashMap<Object, Object> sendOASSignedEncryptedPseudonymMigrationRequestForTableRow(TableRow propertyTableRow) throws IOException, InterruptedException {
        var specData = getSpecDataset();
        var requestData = SpecDataPropertyTable.asMap(specData, propertyTableRow);

        var templateStep = getScenarioData(TemplateOASSignedEncryptedPseudonymMigrationRequestStep.class);
        var request = templateStep.newOasSignedEncryptedPseudonymMigrationRequestFromTemplate();

        if (requestData.containsKey("serviceProviderKeys")) {
            var serviceProviderKeys = (List<String>) requestData.get("serviceProviderKeys");
            request.setServiceProviderKeys(serviceProviderKeys);
        }

        if (requestData.containsKey("signedEncryptedPseudonym")) {
            var signedEncryptedPseudonym = (byte[]) requestData.get("signedEncryptedPseudonym");
            request.setSignedEncryptedPseudonym(signedEncryptedPseudonym);
        }

        if (requestData.containsKey("schemeKeys")) {
            var schemeKeys = (Map<String, byte[]>) requestData.get("schemeKeys");
            request.setSchemeKeys(schemeKeys);
        }

        if (requestData.containsKey("migrationID")) {
            var migrationID = (String) requestData.get("migrationID");
            request.setMigrationID(migrationID);
        }

        if (requestData.containsKey("targetMigrant")) {
            var targetMigrant = (String) requestData.get("targetMigrant");
            request.setTargetMigrant(targetMigrant);
        }

        if (requestData.containsKey("targetMigrantKeySetVersion")) {
            var targetMigrantKeySetVersion = (String) requestData.get("targetMigrantKeySetVersion");
            request.setTargetMigrantKeySetVersion(targetMigrantKeySetVersion);
        }

        // Enforce identical MigrationID on templated requests if requirements is enforced on the template
        var serviceProviderKeys = request.getServiceProviderKeys();
        var requireMatchingIdStep = getScenarioData(RequireMatchingMigrationIdForSignedEncryptedPseudonymMigrationStep.class);
        Optional.ofNullable(requireMatchingIdStep).ifPresent(it -> it.assertIdenticalMigrationIdForAllEpMigrationKeys(serviceProviderKeys));

        var uri = getSpecData(URI.class);
        return sendJsonRequest(uri, request);
    }

}


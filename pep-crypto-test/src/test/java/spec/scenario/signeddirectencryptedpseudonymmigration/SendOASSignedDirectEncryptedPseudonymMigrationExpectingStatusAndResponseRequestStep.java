package spec.scenario.signeddirectencryptedpseudonymmigration;

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

public class SendOASSignedDirectEncryptedPseudonymMigrationExpectingStatusAndResponseRequestStep
        extends BasicStep {

    @Step("Send template OASSignedDirectEncryptedPseudonymMigrationRequest using values and assertions <table>")
    public void sendOASSignedDirectEncryptedPseudonymMigrationRequest(Table propertyTable) throws IOException, InterruptedException {
        for (var propertyTableRow : propertyTable.getTableRows()) {
            var exchange = sendOASSignedDirectEncryptedPseudonymMigrationRequestForTableRow(propertyTableRow);
            assertResponse(propertyTableRow, exchange);
        }
    }

    private void assertResponse(TableRow propertyTableRow, HashMap<Object, Object> exchange) {
        var asserter = getAsserter();

        // Status
        var response = (HttpResponse) exchange.get(HttpResponse.class);
        var statusCode = String.valueOf(response.statusCode());
        var expectedStatus = propertyTableRow.getCell("status");
        asserter.addAssertTrue("\nExpect status '" + statusCode + "' does not match HTTP status.\n\tExpected: " + propertyTableRow.toString() + "\n\tActual :" + statusCode, statusCode.equals(expectedStatus));

        // Token in body
        var expectedTokenInBody = propertyTableRow.getCell("token");
        var body = (String) response.body();
        asserter.addAssertTrue("\nExpected token '" + expectedTokenInBody + "' not found in body.\n\tExpected: " + propertyTableRow.toString() + "\n\tActual :" + body, body.contains(expectedTokenInBody));
    }

    private HashMap<Object, Object> sendOASSignedDirectEncryptedPseudonymMigrationRequestForTableRow(TableRow propertyTableRow) throws IOException, InterruptedException {
        var specData = getSpecDataset();
        var requestData = SpecDataPropertyTable.asMap(specData, propertyTableRow);

        var templateStep = getScenarioData(TemplateOASSignedDirectEncryptedPseudonymMigrationRequestStep.class);
        var oasSignedDirectEncryptedPseudonymMigrationRequest = templateStep.newOasSignedDirectEncryptedPseudonymMigrationRequestFromTemplate();

        if (requestData.containsKey("serviceProviderKeys")) {
            var serviceProviderKeys = (List<String>) requestData.get("serviceProviderKeys");
            oasSignedDirectEncryptedPseudonymMigrationRequest.setServiceProviderKeys(serviceProviderKeys);
        }

        if (requestData.containsKey("signedDirectEncryptedPseudonym")) {
            var signedDirectEncryptedPseudonym = (byte[]) requestData.get("signedDirectEncryptedPseudonym");
            oasSignedDirectEncryptedPseudonymMigrationRequest.setSignedDirectEncryptedPseudonym(signedDirectEncryptedPseudonym);
        }

        if (requestData.containsKey("schemeKeys")) {
            var schemeKeys = (Map<String, byte[]>) requestData.get("schemeKeys");
            oasSignedDirectEncryptedPseudonymMigrationRequest.setSchemeKeys(schemeKeys);
        }

        if (requestData.containsKey("migrationID")) {
            var migrationID = (String) requestData.get("migrationID");
            oasSignedDirectEncryptedPseudonymMigrationRequest.setMigrationID(migrationID);
        }

        if (requestData.containsKey("targetMigrant")) {
            var targetMigrant = (String) requestData.get("targetMigrant");
            oasSignedDirectEncryptedPseudonymMigrationRequest.setTargetMigrant(targetMigrant);
        }

        if (requestData.containsKey("targetMigrantKeySetVersion")) {
            var targetMigrantKeySetVersion = (String) requestData.get("targetMigrantKeySetVersion");
            oasSignedDirectEncryptedPseudonymMigrationRequest.setTargetMigrantKeySetVersion(targetMigrantKeySetVersion);
        }

        if (requestData.containsKey("authorizedParty")) {
            var authorizedParty = (String) requestData.get("authorizedParty");
            oasSignedDirectEncryptedPseudonymMigrationRequest.setAuthorizedParty(authorizedParty);
        }

        // Enforce identical MigrationID on templated requests if requirements is enforced on the template
        var serviceProviderKeys = oasSignedDirectEncryptedPseudonymMigrationRequest.getServiceProviderKeys();
        var requireMatchingIdStep = getScenarioData(RequireMatchingMigrationIdForSignedDirectEncryptedPseudonymMigrationStep.class);
        Optional.ofNullable(requireMatchingIdStep).ifPresent(it -> it.assertIdenticalMigrationIdForAllEpMigrationKeys(serviceProviderKeys));

        var uri = getSpecData(URI.class);
        return sendJsonRequest(uri, oasSignedDirectEncryptedPseudonymMigrationRequest);
    }

}


package spec.scenario.signeddirectencryptedidentity;

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

public class SendTemplateOasSignedDirectEncryptedIdentityRequestStep
        extends BasicStep {

    @Step("Send template OASSignedDirectEncryptedIdentityRequest using values and assertions <table>")
    public void sendTemplateOasSignedDirectEncryptedIdentityRequestUsingValuesAndAssertions(Table propertyTable) throws IOException, InterruptedException {
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

        var templateStep = getScenarioData(TemplateOASSignedDirectEncryptedIdentityRequestStep.class);
        var request = templateStep.newOasSignedDirectEncryptedIdentityRequestStepFromTemplate();

        if (requestData.containsKey("serviceProviderKeys")) {
            var serviceProviderKeys = (List<String>) requestData.get("serviceProviderKeys");
            request.setServiceProviderKeys(serviceProviderKeys);
        }

        if (requestData.containsKey("signedDirectEncryptedIdentity")) {
            var signedDirectEncryptedIdentity = (byte[]) requestData.get("signedDirectEncryptedIdentity");
            request.setSignedDirectEncryptedIdentity(signedDirectEncryptedIdentity);
        }

        if (requestData.containsKey("schemeKeys")) {
            var schemeKeys = (Map<String, byte[]>) requestData.get("schemeKeys");
            request.setSchemeKeys(schemeKeys);
        }

        if (requestData.containsKey("authorizedParty")) {
            var authorizedParty = (String) requestData.get("authorizedParty");
            request.setAuthorizedParty(authorizedParty);
        }

        var uri = getSpecData(URI.class);
        return sendJsonRequest(uri, request);
    }

}

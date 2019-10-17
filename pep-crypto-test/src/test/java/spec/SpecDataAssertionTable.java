package spec;

import client.Asserter;
import com.jayway.jsonpath.JsonPath;
import com.thoughtworks.gauge.Table;
import groovy.json.JsonOutput;
import nl.logius.pepcrypto.lib.TestResources;
import org.json.JSONException;

import java.util.Map;

public enum SpecDataAssertionTable {
    ;

    public static void evaluate(Map expectedValuesDataSource, String actualValuesJson, Table assertionTable, Asserter asserter) {
        var tableRows = assertionTable.getTableRows();

        for (var tableRow : tableRows) {
            var actualValueColumnValue = tableRow.getCell("actualValue");
            var expectedValueColumnValue = tableRow.getCell("expectedValue");
            var expectedValueExpression = expectedValueColumnValue.replaceAll("^\\s*\\{\\{\\s*([\\S]*)\\s*$", "$1");
            var actualValue = JsonPath.read(actualValuesJson, actualValueColumnValue);

            var expectedValueTypeColumnValue = tableRow.getCell("expectedValueType");
            var expectedValueTypeExpression = expectedValueTypeColumnValue
                                                      .replaceAll("^\\s*([\\S]*)\\s*\\}\\}\\s*$", "$1");
            var expectedValue = JsonPath.read(expectedValuesDataSource, expectedValueExpression);

            switch (expectedValueTypeExpression) {
                case "binary":
                    var expectedBase64String = TestResources.resourceToBase64((String) expectedValue);
                    var actualBase64String = String.valueOf(actualValue);
                    asserter.addAssertEquals(expectedValueExpression, expectedBase64String, actualBase64String);
                    break;
                case "string_resource":
                    var expectedResourceString = TestResources.resourceToString((String) expectedValue);
                    var actualResourceString = String.valueOf(actualValue);
                    asserter.addAssertEquals(expectedValueExpression, expectedResourceString, actualResourceString);
                    break;
                case "string":
                    var expectedString = String.valueOf(expectedValue);
                    var actualString = String.valueOf(actualValue);
                    asserter.addAssertEquals(expectedValueExpression, expectedString, actualString);
                    break;
                case "string_signature":
                    var expectedSignatureValueJson = TestResources.resourceToSignatureValue((String) expectedValue);
                    var actualSignatureValueJson = JsonOutput.toJson(actualValue);

                    try {
                        asserter.addAssertJsonCompareResult(expectedValueExpression, expectedSignatureValueJson, actualSignatureValueJson);
                    } catch (JSONException cause) {
                        asserter.addException(expectedValueExpression, cause);
                    }

                    break;
                case "json":
                    var expectedValueJson = JsonOutput.toJson(expectedValue);
                    try {
                        asserter.addAssertJsonCompareResult(expectedValueExpression, expectedValueJson, (String) actualValue);
                    } catch (JSONException cause) {
                        asserter.addException(expectedValueExpression, cause);
                    }

                    break;
                default:
                    throw new UnsupportedOperationException(expectedValueTypeExpression);
            }
        }
    }
}

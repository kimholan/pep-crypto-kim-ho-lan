package spec;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import data.SchemeKeys;
import nl.logius.pepcrypto.lib.TestResources;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public enum SpecDataPropertyTable {
    ;

    public static Map<String, Object> asMap(Map propertyDataSource, Table propertyTable) {
        return asMap(propertyDataSource, propertyTable,
                "propertyName",
                "propertyValue",
                "propertyFilter");
    }

    public static Map<String, Object> asMap(Map propertyDataSource, Table propertyTable,
                                            String propertyNameColumnName,
                                            String propertyValueColumnName,
                                            String propertyFilterColumnName) {
        var tableRows = propertyTable.getTableRows();
        var requestData = new HashMap<String, Object>();

        for (var tableRow : tableRows) {
            var rowAsMap = asMap(propertyDataSource, tableRow, propertyNameColumnName, propertyValueColumnName, propertyFilterColumnName);

            requestData.putAll(rowAsMap);
        }

        return requestData;
    }

    public static Map<String, Object> asMap(Map propertyDataSource, TableRow propertyTableRow) {
        return asMap(propertyDataSource, propertyTableRow,
                "propertyName",
                "propertyValue",
                "propertyFilter");
    }

    public static Map<String, Object> asMap(Map propertyDataSource, TableRow propertyTableRow,
                                            String propertyNameColumnName,
                                            String propertyValueColumnName,
                                            String propertyFilterColumnName) {

        var propertyNameColumnValue = propertyTableRow.getCell(propertyNameColumnName);
        var propertyValueColumnValue = propertyTableRow.getCell(propertyValueColumnName);
        var propertyValueExpression = propertyValueColumnValue.replaceAll("^\\s*\\{\\{\\s*([\\S]*)\\s*$", "$1");
        var propertyFilterColumnValue = propertyTableRow.getCell(propertyFilterColumnName);
        var propertyFilterExpression = propertyFilterColumnValue.replaceAll("^\\s*([\\S]*)\\s*\\}\\}\\s*$", "$1");

        // expresion
        var expressionParts = propertyValueExpression.split("\\|");
        var jsonPath = expressionParts[0];

        Object propertyValue;
        try {
            propertyValue = JsonPath.read(propertyDataSource, jsonPath);
        } catch (PathNotFoundException cause) {
            propertyValue = null;
        }

        Object value = Void.TYPE;
        switch (propertyFilterExpression) {
            case "binary":
                value = TestResources.resourceToByteArray((String) propertyValue);
                break;
            case "string":
                value = propertyValue;
                break;
            case "schemeKeys":
                value = Optional.ofNullable((Map<String, String>) propertyValue)
                                .map(Map::entrySet)
                                .orElse(Collections.emptySet()).stream()
                                .map(SchemeKeys::fromResource)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                break;
            case "base64ResourceMap":
                value = Optional.ofNullable((Map<String, String>) propertyValue)
                                .map(Map::entrySet)
                                .orElse(Collections.emptySet()).stream()
                                .map(TestResources::fromBase64Resource)
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                break;
            case "serviceProviderKeys":
                value = Optional.ofNullable((List) propertyValue)
                                .orElse(Collections.emptyList()).stream()
                                .map(it -> TestResources.resourceToString((String) it))
                                .collect(Collectors.toList());
                break;
        }

        return Collections.singletonMap(propertyNameColumnValue, value);

    }

}

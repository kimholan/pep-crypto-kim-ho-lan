package spec;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;

import java.util.Map;

public class SpecPropertiesStep
        extends BasicStep {

    private Map<String, String> map;

    @Step("Specification properties <table>")
    public void targetDefaultEndpoint(Table table) {
        var tableRows = table.getTableRows();
        var entries = tableRows.stream()
                               .map(it -> Map.entry(it.getCell("propertyName").trim(), it.getCell("propertyValue").trim()))
                               .toArray(Map.Entry[]::new);
        map = Map.ofEntries(entries);
        putSpecData(SpecPropertiesStep.class, this);
    }

    public String get(String string) {
        return map.get(string);
    }

}

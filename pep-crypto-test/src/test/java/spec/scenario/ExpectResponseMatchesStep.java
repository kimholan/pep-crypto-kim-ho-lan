package spec.scenario;

import client.ResponseHolder;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import groovy.json.JsonOutput;
import spec.BasicStep;
import spec.SpecDataAssertionTable;

public class ExpectResponseMatchesStep
        extends BasicStep {

    @Step("Expect response matches <assertionTable>")
    public void expectResponseMatches(Table assertionTable) {
        var responseHolder = getScenarioData(ResponseHolder.class);

        putScenarioData(ExpectResponseMatchesStep.class, this);

        var expectedValuesDataSource = getSpecDataset();
        var asserter = getAsserter();
        var actualValuesJson = JsonOutput.toJson(responseHolder);

        SpecDataAssertionTable.evaluate(expectedValuesDataSource, actualValuesJson, assertionTable, asserter);
    }

}

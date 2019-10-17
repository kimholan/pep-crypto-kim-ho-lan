package spec.scenario;

import client.ResponseHolder;
import com.thoughtworks.gauge.Step;
import spec.BasicStep;

public class ExpectResponseBodyContainsStep
        extends BasicStep {

    @Step("Expect response body contains <token>")
    public void expectResponseBodyContains(String token) {
        var assertionResult = getAsserter();

        putScenarioData(ExpectResponseBodyContainsStep.class, this);
        var responseHolder = getScenarioData(ResponseHolder.class);
        var body = String.valueOf(responseHolder.get("body"));

        assertionResult.addAssertTrue("Expect response body contains <token>", body.contains(token));
    }

}

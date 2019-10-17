package spec;

import com.thoughtworks.gauge.Step;

public class FailIfExpectationsAreNotMetBasicStep
        extends BasicStep {

    @Step("Fail if expectations are not met")
    public void failIfExpectationsAreNotMet() {
        getAsserter().requireNoErrors();
    }

}

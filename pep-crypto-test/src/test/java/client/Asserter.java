package client;

import org.json.JSONException;
import org.junit.Assert;
import org.skyscreamer.jsonassert.JSONCompare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.skyscreamer.jsonassert.JSONCompareMode.STRICT;

public class Asserter {

    private List<String> errorList;

    private List<Map<String, String>> patches;

    public Asserter() {
        errorList = new ArrayList<>();
        patches = new ArrayList<>();
    }

    public List<Map<String, String>> getPatches() {
        return patches;
    }

    public void requireNoErrors() {
        Assert.assertTrue(errorList.toString(), errorList.isEmpty());
    }

    public void addAssertTrue(String message, boolean value) {
        try {
            Assert.assertTrue(message, value);
        } catch (AssertionError cause) {
            errorList.add(cause.getMessage());
        }
    }

    public void addAssertEquals(String message, Object expectedValue, Object actualValue) {
        try {
            Assert.assertEquals(message, expectedValue, actualValue);
        } catch (AssertionError cause) {
            errorList.add(cause.getMessage());
        }
    }

    public void addException(String expectedValueExpression, Exception exception) {
        errorList.add(exception.getMessage());
    }

    public void addAssertJsonCompareResult(String expectedValueExpression, String expectedJson, String actualJson) throws JSONException {
        var jsonCompareResult = JSONCompare.compareJSON(expectedJson, actualJson, STRICT);
        try {
            Assert.assertFalse(expectedValueExpression, jsonCompareResult.failed());
        } catch (AssertionError cause) {
            patches.add(Collections.singletonMap(expectedValueExpression, actualJson));
            errorList.add(cause.getMessage() + ":" + jsonCompareResult.getMessage());
        }
    }

}

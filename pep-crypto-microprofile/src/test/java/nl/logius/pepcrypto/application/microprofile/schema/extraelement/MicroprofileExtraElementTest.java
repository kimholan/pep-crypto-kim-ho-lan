package nl.logius.pepcrypto.application.microprofile.schema.extraelement;

import generated.asn1.ExtraElements;
import generated.asn1.ExtraElementsKeyValuePair;
import generated.asn1.VariableValueType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MicroprofileExtraElementTest {

    private static final String KEY = "key";

    private static final String VALUE = "value";

    @Test
    public void newInstance() {
        var vvt = new VariableValueType();
        vvt.setText(VALUE);
        var extraElement = ExtraElementsKeyValuePair.builder()
                                                    .key(KEY)
                                                    .value(vvt)
                                                    .build();
        var expected = ExtraElements.builder()
                                    .extraelementskeyvaluepair(List.of(extraElement))
                                    .build();

        var actual = MicroprofileExtraElement.newInstance(expected);
        assertEquals(1, actual.size());

        var oasExtraElement = actual.get(0);
        assertEquals(KEY, oasExtraElement.getKey());
        assertEquals(VALUE, oasExtraElement.getValue());
    }

}

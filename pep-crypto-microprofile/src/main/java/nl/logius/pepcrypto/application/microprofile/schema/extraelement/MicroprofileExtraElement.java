package nl.logius.pepcrypto.application.microprofile.schema.extraelement;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.asn1.ExtraElements;
import generated.asn1.ExtraElementsKeyValuePair;
import generated.nl.logius.pepcrypto.openapi.model.OASExtraElement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonPropertyOrder({
        "key",
        "value",
})
public class MicroprofileExtraElement
        extends OASExtraElement {

    public static List<OASExtraElement> newInstance(ExtraElements extraElements) {
        return Optional.ofNullable(extraElements)
                       .map(MicroprofileExtraElement::extraElementsKeyValuePair)
                       .orElse(null);
    }

    private static List<OASExtraElement> extraElementsKeyValuePair(ExtraElements diversifier) {
        return diversifier.getExtraelementskeyvaluepair().stream()
                          .map(MicroprofileExtraElement::newExtraElementsKeyValuePair)
                          .collect(Collectors.toList());
    }

    private static OASExtraElement newExtraElementsKeyValuePair(ExtraElementsKeyValuePair keyValuePair) {
        return new OASExtraElement()
                       .key(keyValuePair.getKey())
                       .value(keyValuePair.getValue().asn1Value());
    }

}

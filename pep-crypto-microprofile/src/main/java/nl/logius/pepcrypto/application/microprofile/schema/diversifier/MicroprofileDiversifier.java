package nl.logius.pepcrypto.application.microprofile.schema.diversifier;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.asn1.Diversifier;
import generated.asn1.DiversifierKeyValuePair;
import generated.nl.logius.pepcrypto.openapi.model.OASDiversifier;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Extends OAS-model to processRawInput internal object model to OAS model whilst enforcing property during serialization.
 */
@JsonPropertyOrder({
        "key",
        "value",
})
public class MicroprofileDiversifier
        extends OASDiversifier {

    public static List<OASDiversifier> newInstance(Diversifier diversifier) {
        return Optional.ofNullable(diversifier)
                       .map(MicroprofileDiversifier::diversifierKeyValuePair)
                       .orElse(null);
    }

    private static List<OASDiversifier> diversifierKeyValuePair(Diversifier diversifier) {
        return diversifier.getDiversifierkeyvaluepair().stream()
                          .map(MicroprofileDiversifier::newDiversifierKeyPair)
                          .collect(Collectors.toList());
    }

    private static OASDiversifier newDiversifierKeyPair(DiversifierKeyValuePair keyValuePair) {
        return new OASDiversifier()
                       .key(keyValuePair.getKey())
                       .value(keyValuePair.getValue());
    }

}


package nl.logius.pepcrypto.lib.asn1;

import generated.asn1.ExtraElementsKeyValuePair;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Asn1ExtraElements {

    List<ExtraElementsKeyValuePair> getExtraelementskeyvaluepair();

    /**
     * String representation of ExtraElements for convenience.
     */
    default String asn1ExtraelementskeyvaluepairAsString() {
        return Optional.of(getExtraelementskeyvaluepair())
                       .orElseGet(Collections::emptyList)
                       .stream()
                       .map(it -> it.getKey() + "=" + it.getValue().asn1ValueAsString())
                       .collect(Collectors.joining(","));
    }

}

package nl.logius.pepcrypto.lib.asn1;

import generated.asn1.DiversifierKeyValuePair;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Asn1Diversifier {

    List<DiversifierKeyValuePair> getDiversifierkeyvaluepair();

    default String asn1DiversifierString() {
        return Optional.ofNullable(getDiversifierkeyvaluepair())
                       .stream()
                       .flatMap(Collection::stream)
                       .map(it -> it.getKey() + "=" + it.getValue())
                       .collect(Collectors.joining(","));
    }

}

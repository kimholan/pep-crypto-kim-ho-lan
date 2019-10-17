package nl.logius.pepcrypto.lib.crypto;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Identifiers the scheme key set of a key or the key used to generate PEP-structure
 * <p>
 * The identifier is a (schemeVersion, schemeKeySetVersion)-combination. The schemeKeySetVersion
 * may be labelled as schemeKeyVersion in older specifications.
 */
public interface PepSchemeKeySetId {

    static Comparator<PepSchemeKeySetId> comparator() {
        return Comparator.comparing(PepSchemeKeySetId::getSchemeVersion)
                         .thenComparing(PepSchemeKeySetId::getSchemeKeySetVersion);
    }

    static boolean isSupportedSchemeVersion(int schemeVersion) {
        return 1 == schemeVersion;
    }

    BigInteger getSchemeKeySetVersion();

    BigInteger getSchemeVersion();

    default boolean isMatchingPepSchemeKeySetId(PepSchemeKeySetId that) {
        return comparator().compare(this, that) == 0;
    }

    default <T extends PepSchemeKeySetId> List<T> findAllWithMatchingPepSchemeKeySetId(List<T> items) {
        return Optional.ofNullable(items).stream()
                       .flatMap(Collection::stream)
                       .filter(this::isMatchingPepSchemeKeySetId)
                       .collect(Collectors.toList());
    }

}

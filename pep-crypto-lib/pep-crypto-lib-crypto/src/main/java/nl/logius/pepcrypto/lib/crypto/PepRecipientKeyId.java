package nl.logius.pepcrypto.lib.crypto;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;

/**
 * Identifies the key or the key used to produce a PEP-structure excluding the key type.
 * <p>
 * Keys are identifiable by the combination of (schemeVersion, schemeKeySetVersion, recipient, recipientKeySetVersion).
 * The recipient refers to the key holder and maybe be named differently depending on the key type.
 * In addition to the aforementioned fields there maybe one or more additional properties that determine the key equivalence,
 * such as 'diversifier' and 'migrationid'.
 */
public interface PepRecipientKeyId
        extends PepSchemeKeySetId {

    /**
     * Comparator using (schemeVersion, schemeKeySetVersion, recipient, recipientKeySetVersion).
     */
    static Comparator<PepRecipientKeyId> comparator() {
        return comparing(PepRecipientKeyId::getSchemeVersion)
                       .thenComparing(PepRecipientKeyId::getSchemeKeySetVersion)
                       .thenComparing(PepRecipientKeyId::getRecipient)
                       .thenComparing(PepRecipientKeyId::getRecipientKeySetVersion);
    }

    static Comparator<PepRecipientKeyId> comparatorWithDiversifier() {
        return comparator().thenComparing(PepRecipientKeyId::getDiversifier, nullsLast(naturalOrder()));
    }

    static Comparator<PepRecipientKeyId> comparatorWithMigrationId() {
        return comparatorWithDiversifier()
                       .thenComparing(PepRecipientKeyId::getMigrationId, nullsLast(naturalOrder()));
    }

    String getRecipient();

    BigInteger getRecipientKeySetVersion();

    String getDiversifier();

    String getMigrationId();

    default boolean isMatchingPepRecipientKeyId(PepRecipientKeyId that) {
        return isMatchingPepRecipientKeyId(comparator(), that);
    }

    default boolean isMatchingPepRecipientKeyId(Comparator<PepRecipientKeyId> comparator, PepRecipientKeyId that) {
        return comparator.compare(this, that) == 0;
    }

    default String versionString() {
        return getSchemeVersion() + ":" +
                       getSchemeKeySetVersion() + ":" +
                       getRecipient() + ":" +
                       getRecipientKeySetVersion();
    }

    default <T extends PepRecipientKeyId> List<T> findAllWithMatchingPepRecipientKeyId(List<T> items) {
        return Optional.ofNullable(items).stream()
                       .flatMap(Collection::stream)
                       .filter(this::isMatchingPepRecipientKeyId)
                       .collect(Collectors.toList());
    }

    default <T extends PepRecipientKeyId> T findAnyWithMatchingPepRecipientKeyId(List<T> items) {
        return Optional.of(items).stream()
                       .flatMap(Collection::stream)
                       .filter(this::isMatchingPepRecipientKeyId)
                       .findAny()
                       .orElse(null);
    }

    default boolean hasAnyMatchingPepRecipientKeyId(List<? extends PepRecipientKeyId> items) {
        return Optional.ofNullable(findAnyWithMatchingPepRecipientKeyId(items))
                       .isPresent();
    }

}

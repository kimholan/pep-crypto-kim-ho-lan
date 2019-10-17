package nl.logius.pepcrypto.lib.pem;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.DIVERSIFIER;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.MIGRATION_ID;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.RECIPIENT;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.RECIPIENT_KEY_SET_VERSION;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.SCHEME_KEY_SET_VERSION;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.SCHEME_KEY_VERSION;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.SCHEME_VERSION;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.SOURCE_MIGRANT;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.SOURCE_MIGRANT_KEY_SET_VERSION;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.TARGET_MIGRANT;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.TARGET_MIGRANT_KEY_SET_VERSION;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.TYPE;

enum PemEcPrivateKeyType {

    DEPRECATED_EI_DECRYPTION(
            TYPE.asEntryWithValue("EI Decryption"),
            List.of(
                    SCHEME_VERSION,
                    TYPE,
                    SCHEME_KEY_VERSION,
                    RECIPIENT,
                    RECIPIENT_KEY_SET_VERSION
            )
    ),
    DEPRECATED_EP_DECRYPTION(
            TYPE.asEntryWithValue("EP Decryption"),
            List.of(
                    SCHEME_VERSION,
                    TYPE,
                    SCHEME_KEY_VERSION,
                    RECIPIENT,
                    RECIPIENT_KEY_SET_VERSION
            )
    ),
    DEPRECATED_EP_CLOSING(
            TYPE.asEntryWithValue("EP Closing"),
            List.of(
                    SCHEME_VERSION,
                    TYPE,
                    SCHEME_KEY_VERSION,
                    RECIPIENT,
                    RECIPIENT_KEY_SET_VERSION
            )
    ),
    DEPRECATED_DRKI(
            TYPE.asEntryWithValue("DRKi"),
            List.of(
                    SCHEME_VERSION,
                    TYPE,
                    SCHEME_KEY_VERSION,
                    RECIPIENT,
                    RECIPIENT_KEY_SET_VERSION
            ),
            List.of(DIVERSIFIER)
    ),
    DEPRECATED_EP_MIGRATION_SOURCE(
            TYPE.asEntryWithValue("EP migration source"),
            List.of(
                    SCHEME_VERSION,
                    TYPE,
                    SCHEME_KEY_VERSION,
                    SOURCE_MIGRANT,
                    SOURCE_MIGRANT_KEY_SET_VERSION,
                    TARGET_MIGRANT,
                    TARGET_MIGRANT_KEY_SET_VERSION,
                    MIGRATION_ID
            )
    ),
    DEPRECATED_EP_MIGRATION_TARGET(
            TYPE.asEntryWithValue("EP migration target"),
            List.of(
                    SCHEME_VERSION,
                    TYPE,
                    SCHEME_KEY_VERSION,
                    SOURCE_MIGRANT,
                    SOURCE_MIGRANT_KEY_SET_VERSION,
                    TARGET_MIGRANT,
                    TARGET_MIGRANT_KEY_SET_VERSION,
                    MIGRATION_ID
            )
    ),
    EI_DECRYPTION(
            TYPE.asEntryWithValue("EI Decryption"),
            List.of(
                    SCHEME_VERSION,
                    TYPE,
                    SCHEME_KEY_SET_VERSION,
                    RECIPIENT,
                    RECIPIENT_KEY_SET_VERSION
            )
    ),
    EP_DECRYPTION(
            TYPE.asEntryWithValue("EP Decryption"),
            List.of(
                    SCHEME_VERSION,
                    TYPE,
                    SCHEME_KEY_SET_VERSION,
                    RECIPIENT,
                    RECIPIENT_KEY_SET_VERSION
            )),
    EP_CLOSING(
            TYPE.asEntryWithValue("EP Closing"),
            List.of(
                    SCHEME_VERSION,
                    TYPE,
                    SCHEME_KEY_SET_VERSION,
                    RECIPIENT,
                    RECIPIENT_KEY_SET_VERSION
            )
    ),
    DRKI(
            TYPE.asEntryWithValue("DRKi"),
            List.of(
                    SCHEME_VERSION,
                    TYPE,
                    SCHEME_KEY_SET_VERSION,
                    RECIPIENT,
                    RECIPIENT_KEY_SET_VERSION
            ),
            List.of(DIVERSIFIER)
    ),
    EP_MIGRATION_SOURCE(
            TYPE.asEntryWithValue("EP migration source"),
            List.of(
                    SCHEME_VERSION,
                    TYPE,
                    SCHEME_KEY_SET_VERSION,
                    SOURCE_MIGRANT,
                    SOURCE_MIGRANT_KEY_SET_VERSION,
                    TARGET_MIGRANT,
                    TARGET_MIGRANT_KEY_SET_VERSION,
                    MIGRATION_ID
            ),
            List.of(DIVERSIFIER)
    ),
    EP_MIGRATION_TARGET(
            TYPE.asEntryWithValue("EP migration target"),
            List.of(
                    SCHEME_VERSION,
                    TYPE,
                    SCHEME_KEY_SET_VERSION,
                    SOURCE_MIGRANT,
                    SOURCE_MIGRANT_KEY_SET_VERSION,
                    TARGET_MIGRANT,
                    TARGET_MIGRANT_KEY_SET_VERSION,
                    MIGRATION_ID
            ),
            List.of(DIVERSIFIER)
    );

    private final List<PemEcPrivateKeyHeader> requiredHeaders;

    private final List<PemEcPrivateKeyHeader> optionalHeaders;

    private final List<PemEcPrivateKeyHeader> specifiedHeaders;

    private final Entry<PemEcPrivateKeyHeader, String> typeValue;

    PemEcPrivateKeyType(Entry<PemEcPrivateKeyHeader, String> typeValue, List<PemEcPrivateKeyHeader> requiredHeaders) {
        this(typeValue, requiredHeaders, emptyList());
    }

    PemEcPrivateKeyType(Entry<PemEcPrivateKeyHeader, String> typeValue, List<PemEcPrivateKeyHeader> requiredHeaders, List<PemEcPrivateKeyHeader> optionalHeaders) {
        this.typeValue = typeValue;
        this.requiredHeaders = requiredHeaders;
        this.optionalHeaders = optionalHeaders;
        specifiedHeaders = Stream.of(requiredHeaders, optionalHeaders)
                                 .flatMap(Collection::stream)
                                 .collect(toList());
    }

    public static List<PemEcPrivateKeyType> pemEcPrivateKeyTypeCandidates(List<Entry<String, String>> entries) {
        return Arrays.stream(PemEcPrivateKeyType.values())
                     .filter(it -> it.hasMatchingTypeHeader(entries))
                     .sorted(Comparator.comparing(PemEcPrivateKeyType::ordinal).reversed())
                     .collect(toList());
    }

    public boolean isEiDecryptionKey() {
        return Set.of(EI_DECRYPTION, DEPRECATED_EI_DECRYPTION).contains(this);
    }

    public boolean isEpDecryptionKey() {
        return Set.of(EP_DECRYPTION, DEPRECATED_EP_DECRYPTION).contains(this);
    }

    public boolean isEpClosingKey() {
        return Set.of(EP_CLOSING, DEPRECATED_EP_CLOSING).contains(this);
    }

    public boolean isDrkiKey() {
        return Set.of(DRKI, DEPRECATED_DRKI).contains(this);
    }

    public boolean isSourceMigrationKey() {
        return Set.of(DEPRECATED_EP_MIGRATION_SOURCE, EP_MIGRATION_SOURCE).contains(this);
    }

    public boolean isTargetMigrationKey() {
        return Set.of(DEPRECATED_EP_MIGRATION_TARGET, EP_MIGRATION_TARGET).contains(this);
    }

    private boolean hasMatchingTypeHeader(List<Entry<String, String>> entries) {
        var key = typeValue.getKey();
        var type = typeValue.getValue();
        return entries.stream()
                      .filter(key::matchesKey)
                      .map(Entry::getValue)
                      .anyMatch(type::equals);
    }

    public boolean valid(List<Entry<String, String>> pemHeaderEntries) {
        return Optional.ofNullable(pemHeaderEntries)
                       .filter(this::hasMatchingTypeHeader)
                       .filter(this::isRequiredHeadersValid)
                       .filter(this::isOptionalHeadersValid)
                       .isPresent();
    }

    private boolean isOptionalHeadersValid(List<Entry<String, String>> pemHeaderEntries) {
        var matching = pemHeaderEntries.stream()
                                       .filter(this::isOptionalHeader)
                                       .collect(toList());

        var unique = matching.stream()
                             .map(Entry::getKey)
                             .distinct()
                             .collect(toList());

        return matching.size() == unique.size();
    }

    private boolean isRequiredHeadersValid(List<Entry<String, String>> pemHeaderEntries) {
        var matching = pemHeaderEntries.stream()
                                       .filter(this::isRequiredHeader)
                                       .collect(toList());

        var unique = matching.stream()
                             .map(Entry::getKey)
                             .distinct()
                             .collect(toList());

        return unique.size() == requiredHeaders.size() && matching.size() == unique.size();
    }

    private boolean isOptionalHeader(Entry<String, String> it) {
        return isEntryForHeader(it, optionalHeaders);
    }

    private boolean isRequiredHeader(Entry<String, String> it) {
        return isEntryForHeader(it, requiredHeaders);
    }

    private boolean isEntryForHeader(Entry<String, String> it, List<PemEcPrivateKeyHeader> requiredHeaders) {
        var key = it.getKey();
        return requiredHeaders.stream()
                              .map(PemEcPrivateKeyHeader::getHeaderKeyName)
                              .anyMatch(key::equals);
    }

    private Entry<PemEcPrivateKeyHeader, String> asSpecifiedHeader(Entry<String, String> entry) {
        return specifiedHeaders.stream()
                               .filter(it -> it.matchesKey(entry))
                               .map(it -> Map.entry(it, entry.getValue()))
                               .findFirst()
                               .orElse(null);
    }

    public Map<PemEcPrivateKeyHeader, String> mapBySpecifiedHeaders(List<Entry<String, String>> entries) {
        return entries.stream()
                      .map(this::asSpecifiedHeader)
                      .filter(Objects::nonNull)
                      .collect(toUnmodifiableMap(Entry::getKey, Entry::getValue));
    }

}

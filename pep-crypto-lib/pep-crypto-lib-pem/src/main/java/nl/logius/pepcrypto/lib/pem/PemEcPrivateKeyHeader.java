package nl.logius.pepcrypto.lib.pem;

import java.util.AbstractMap;
import java.util.Map.Entry;
import java.util.Optional;

public enum PemEcPrivateKeyHeader {

    TYPE("Type"),
    SCHEME_VERSION("SchemeVersion"),
    SCHEME_KEY_VERSION("SchemeKeyVersion"),
    SCHEME_KEY_SET_VERSION("SchemeKeySetVersion"),
    RECIPIENT("Recipient"),
    RECIPIENT_KEY_SET_VERSION("RecipientKeySetVersion"),
    DIVERSIFIER("Diversifier"),
    SOURCE_MIGRANT("SourceMigrant"),
    SOURCE_MIGRANT_KEY_SET_VERSION("SourceMigrantKeySetVersion"),
    TARGET_MIGRANT("TargetMigrant"),
    TARGET_MIGRANT_KEY_SET_VERSION("TargetMigrantKeySetVersion"),
    MIGRATION_ID("MigrationID"),
    ;

    private final String headerKeyName;

    PemEcPrivateKeyHeader(String headerKeyName) {
        this.headerKeyName = headerKeyName;
    }

    boolean matchesKey(Entry<String, String> entry) {
        return Optional.ofNullable(entry)
                       .map(Entry::getKey)
                       .filter(headerKeyName::equals)
                       .isPresent();
    }

    public String getHeaderKeyName() {
        return headerKeyName;
    }

    Entry<PemEcPrivateKeyHeader, String> asEntryWithValue(String value) {
        return new AbstractMap.SimpleEntry<>(this, value);
    }
}


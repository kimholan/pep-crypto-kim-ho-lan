package nl.logius.pepcrypto.lib.pem;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

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
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyType.DEPRECATED_EP_MIGRATION_SOURCE;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyType.DEPRECATED_EP_MIGRATION_TARGET;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyType.EP_MIGRATION_SOURCE;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyType.EP_MIGRATION_TARGET;

public class PemEcPrivateKeyValue
        implements PemEcPrivateKey {

    private static final Map<PemEcPrivateKeyType, PemEcPrivateKeyHeader> ALTERNATIVE_RECIPIENT_HEADER = Map.of(
            EP_MIGRATION_SOURCE, SOURCE_MIGRANT,
            DEPRECATED_EP_MIGRATION_SOURCE, SOURCE_MIGRANT,
            EP_MIGRATION_TARGET, TARGET_MIGRANT,
            DEPRECATED_EP_MIGRATION_TARGET, TARGET_MIGRANT
    );

    private static final Map<PemEcPrivateKeyType, PemEcPrivateKeyHeader> ALTERNATIVE_RECIPIENT_KEY_SET_VERSION_HEADER = Map.of(
            EP_MIGRATION_SOURCE, SOURCE_MIGRANT_KEY_SET_VERSION,
            DEPRECATED_EP_MIGRATION_SOURCE, SOURCE_MIGRANT_KEY_SET_VERSION,
            EP_MIGRATION_TARGET, TARGET_MIGRANT_KEY_SET_VERSION,
            DEPRECATED_EP_MIGRATION_TARGET, TARGET_MIGRANT_KEY_SET_VERSION
    );

    private final BigInteger privateKeyValue;

    private final ECPoint publicKeyValue;

    private Map<PemEcPrivateKeyHeader, String> specifiedHeaders;

    private PemEcPrivateKeyType pemEcPrivateKeyType;

    PemEcPrivateKeyValue(PemEcPrivateKeyType pemEcPrivateKeyType, List<Map.Entry<String, String>> specifiedHeaders, BigInteger privateKeyValue, ECPoint publicKeyValue) {
        this.pemEcPrivateKeyType = pemEcPrivateKeyType;
        this.specifiedHeaders = pemEcPrivateKeyType.mapBySpecifiedHeaders(specifiedHeaders);
        this.privateKeyValue = privateKeyValue;
        this.publicKeyValue = publicKeyValue;
    }

    @Override
    public BigInteger getPrivateKey() {
        return privateKeyValue;
    }

    @Override
    public String getSpecifiedHeader(PemEcPrivateKeyHeader header) {
        return specifiedHeaders.get(header);
    }

    @Override
    public ECPoint getPublicKey() {
        return publicKeyValue;
    }

    @Override
    public boolean isMatchingSourceMigrant(String sourceOin) {
        var expected = StringUtils.trimToNull(specifiedHeaders.get(SOURCE_MIGRANT));
        var actual = StringUtils.trimToNull(sourceOin);
        return StringUtils.equals(expected, actual);
    }

    @Override
    public boolean isMatchingSourceMigrantKeySetVersion(BigInteger sourceMigrantKeySetVersion) {
        return Optional.ofNullable(specifiedHeaders.get(SOURCE_MIGRANT_KEY_SET_VERSION))
                       .map(BigInteger::new)
                       .map(it -> it.equals(sourceMigrantKeySetVersion))
                       .orElse(false);
    }

    @Override
    public boolean isMatchingTargetMigrant(String sourceOin) {
        var expected = StringUtils.trimToNull(specifiedHeaders.get(TARGET_MIGRANT));
        var actual = StringUtils.trimToNull(sourceOin);
        return StringUtils.equals(expected, actual);
    }

    @Override
    public boolean isMatchingTargetMigrantKeySetVersion(BigInteger targetMigrantKeySetVersion) {
        return Optional.ofNullable(specifiedHeaders.get(TARGET_MIGRANT_KEY_SET_VERSION))
                       .map(BigInteger::new)
                       .map(it -> it.equals(targetMigrantKeySetVersion))
                       .orElse(false);
    }

    @Override
    public String getType() {
        return specifiedHeaders.get(TYPE);
    }

    @Override
    public String getRecipient() {
        var header = ALTERNATIVE_RECIPIENT_HEADER.getOrDefault(pemEcPrivateKeyType, RECIPIENT);
        return specifiedHeaders.get(header);
    }

    @Override
    public BigInteger getRecipientKeySetVersion() {
        var header = ALTERNATIVE_RECIPIENT_KEY_SET_VERSION_HEADER.getOrDefault(pemEcPrivateKeyType, RECIPIENT_KEY_SET_VERSION);
        var value = specifiedHeaders.get(header);
        return new BigInteger(value);
    }

    @Override
    public String getDiversifier() {
        return specifiedHeaders.get(DIVERSIFIER);
    }

    @Override
    public String getMigrationId() {
        return specifiedHeaders.get(MIGRATION_ID);
    }

    @Override
    public BigInteger getSchemeVersion() {
        var value = specifiedHeaders.get(SCHEME_VERSION);
        return new BigInteger(value);
    }

    @Override
    public BigInteger getSchemeKeySetVersion() {
        return Stream.of(SCHEME_KEY_SET_VERSION, SCHEME_KEY_VERSION)
                     .map(it -> specifiedHeaders.get(it))
                     .filter(NumberUtils::isDigits)
                     .map(BigInteger::new)
                     .findFirst()
                     .orElse(null);
    }

    @Override
    public boolean isEiDecryptionKey() {
        return pemEcPrivateKeyType.isEiDecryptionKey();
    }

    @Override
    public boolean isEpDecryptionKey() {
        return pemEcPrivateKeyType.isEpDecryptionKey();
    }

    @Override
    public boolean isEpClosingKey() {
        return pemEcPrivateKeyType.isEpClosingKey();
    }

    @Override
    public boolean isDrkiKey() {
        return pemEcPrivateKeyType.isDrkiKey();
    }

    @Override
    public boolean isEpMigrationSourceKey() {
        return pemEcPrivateKeyType.isSourceMigrationKey();
    }

    @Override
    public boolean isEpMigrationTargetKey() {
        return pemEcPrivateKeyType.isTargetMigrationKey();
    }

}

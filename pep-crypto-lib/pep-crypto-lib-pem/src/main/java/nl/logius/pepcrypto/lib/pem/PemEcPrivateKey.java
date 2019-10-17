package nl.logius.pepcrypto.lib.pem;

import nl.logius.pepcrypto.lib.crypto.PepRecipientKeyId;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.Optional;

import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.SOURCE_MIGRANT;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.TARGET_MIGRANT;

public interface PemEcPrivateKey
        extends PepRecipientKeyId {

    String getType();

    BigInteger getPrivateKey();

    ECPoint getPublicKey();

    boolean isEpDecryptionKey();

    boolean isEpClosingKey();

    boolean isEiDecryptionKey();

    boolean isDrkiKey();

    boolean isEpMigrationSourceKey();

    boolean isEpMigrationTargetKey();

    boolean isMatchingSourceMigrant(String sourceMigrant);

    boolean isMatchingSourceMigrantKeySetVersion(BigInteger sourceMigrantKeySetVersion);

    boolean isMatchingTargetMigrant(String targetMigrant);

    boolean isMatchingTargetMigrantKeySetVersion(BigInteger targetMigrantKeySetVersion);

    String getSpecifiedHeader(PemEcPrivateKeyHeader header);

    default String getSourceOrRecipient() {
        return Optional.ofNullable(getSpecifiedHeader(SOURCE_MIGRANT))
                       .orElseGet(this::getRecipient);
    }

    default String getTargetOrRecipient() {
        return Optional.ofNullable(getSpecifiedHeader(TARGET_MIGRANT))
                       .orElseGet(this::getRecipient);
    }

}

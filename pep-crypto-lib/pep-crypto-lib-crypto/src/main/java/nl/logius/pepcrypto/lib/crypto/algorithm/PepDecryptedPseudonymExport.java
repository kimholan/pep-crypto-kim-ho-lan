package nl.logius.pepcrypto.lib.crypto.algorithm;

import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationSourcePrivateKey;
import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.UnaryOperator;

import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.MIGRATION_SOURCE_PRIVATE_KEY_WITHIN_BOUND;

public enum PepDecryptedPseudonymExport
        implements PepExceptionDetail {

    /**
     * Pseudonym export conversion failed.
     */
    EXPORT_CONVERSION_FAILED;

    /**
     * Export intermediary pseudonym.
     * <p>
     * See 'The polymorphic eID scheme, version 1.11, 8 July 2019', 9, Protocol 13.
     *
     * @param ecPoint           Encoded Pseudonym to be exported by migration source.
     * @param epMigrationSource Export key to produce the intermediary pseudonym.
     * @return ecPoint converted to an intermediary pseudonym suitable for import by by migration target.
     */
    public static ECPoint dpExport(ECPoint ecPoint, PepEpMigrationSourcePrivateKey epMigrationSource) {
        MIGRATION_SOURCE_PRIVATE_KEY_WITHIN_BOUND.requireWithinBounds(epMigrationSource);

        return EXPORT_CONVERSION_FAILED.call(() -> ecPoint.multiply(epMigrationSource.getValue()));
    }

    public static UnaryOperator<ECPoint> asFunction(PepEpMigrationSourcePrivateKey epMigrationSource) {
        return it -> dpExport(it, epMigrationSource);
    }

}

package nl.logius.pepcrypto.lib.crypto.algorithm;

import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationTargetPrivateKey;
import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.UnaryOperator;

import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.MIGRATION_TARGET_PRIVATE_KEY_WITHIN_BOUND;

public enum PepMigrationIntermediaryPseudonymImport
        implements PepExceptionDetail {

    /**
     * Pseudonym import conversion failed.
     */
    IMPORT_CONVERSION_FAILED;

    /**
     * Import intermediary pseudonym.
     * <p>
     * See 'The polymorphic eID scheme, version 1.11, 8 July 2019', 9, Protocol 13, step 7.
     *
     * @param ecPoint           Pseudonym converted to an intermediary pseudonym for import by target.
     * @param epMigrationTarget Import key for migration.
     * @return ecPoint Pseudonym encoded as an ECPoint for the migration target.
     */
    public static ECPoint pseudonymMigrationImport(ECPoint ecPoint, PepEpMigrationTargetPrivateKey epMigrationTarget) {
        MIGRATION_TARGET_PRIVATE_KEY_WITHIN_BOUND.requireWithinBounds(epMigrationTarget);

        return IMPORT_CONVERSION_FAILED.call(() -> ecPoint.multiply(epMigrationTarget.getValue()));
    }

    public static UnaryOperator<ECPoint> asFunction(PepEpMigrationTargetPrivateKey epMigrationTarget) {
        return it -> pseudonymMigrationImport(it, epMigrationTarget);
    }

}

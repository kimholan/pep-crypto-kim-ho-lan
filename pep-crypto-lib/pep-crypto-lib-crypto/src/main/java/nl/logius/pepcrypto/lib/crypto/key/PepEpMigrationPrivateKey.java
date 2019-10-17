package nl.logius.pepcrypto.lib.crypto.key;

import nl.logius.pepcrypto.lib.crypto.PepCrypto;

import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.MIGRATION_SOURCE_PRIVATE_KEY_WITHIN_BOUND;
import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.MIGRATION_TARGET_PRIVATE_KEY_WITHIN_BOUND;
import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.PSEUDONYM_CLOSING_PRIVATE_KEY_WITHIN_BOUNDS;

public interface PepEpMigrationPrivateKey
        extends PepPrivateKey {

    static PepEpMigrationPrivateKey newInstance(PepEpClosingPrivateKey epClosing, PepEpMigrationSourcePrivateKey epMigrationSource, PepEpMigrationTargetPrivateKey epMigrationTarget) {
        PSEUDONYM_CLOSING_PRIVATE_KEY_WITHIN_BOUNDS.requireWithinBounds(epClosing);
        MIGRATION_SOURCE_PRIVATE_KEY_WITHIN_BOUND.requireWithinBounds(epMigrationSource);
        MIGRATION_TARGET_PRIVATE_KEY_WITHIN_BOUND.requireWithinBounds(epMigrationTarget);

        return () -> epClosing.getValue().multiply(epMigrationTarget.getValue()).multiply(epMigrationSource.getValue()).mod(PepCrypto.getOrder());
    }

}

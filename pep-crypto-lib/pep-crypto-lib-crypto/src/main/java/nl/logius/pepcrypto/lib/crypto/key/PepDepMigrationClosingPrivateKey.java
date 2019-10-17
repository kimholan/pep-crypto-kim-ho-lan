package nl.logius.pepcrypto.lib.crypto.key;

import nl.logius.pepcrypto.lib.crypto.PepCrypto;

import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.DIRECT_RECEIVE_PRIVATE_KEY_WITHIN_BOUNDS;

/**
 * Key derived from other private keys.
 */
public interface PepDepMigrationClosingPrivateKey
        extends PepPrivateKey {

    static PepDepMigrationClosingPrivateKey newInstance(PepDrkiPrivateKey drki, PepEpMigrationPrivateKey epMigration) {
        DIRECT_RECEIVE_PRIVATE_KEY_WITHIN_BOUNDS.requireWithinBounds(drki);

        return () -> drki.getValue().multiply(epMigration.getValue()).mod(PepCrypto.getOrder());
    }

}

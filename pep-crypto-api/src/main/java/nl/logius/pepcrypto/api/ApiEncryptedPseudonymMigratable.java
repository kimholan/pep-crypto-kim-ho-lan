package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedPseudonymMigration;
import nl.logius.pepcrypto.lib.crypto.key.PepEpDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.Function;

/**
 * Decryptable ECPoint requiring a reshuffle and migration to make it specific for the service provider.
 */
public interface ApiEncryptedPseudonymMigratable
        extends ApiReshuffable<PepEpMigrationPrivateKey, PepEpDecryptionPrivateKey> {

    @Override
    default Function<PepEcPointTriplet, ECPoint> getDecryptionFunction() {
        return PepEncryptedPseudonymMigration.asFunction(getClosingPepPrivateKey(), getDecryptionPepPrivateKey());
    }

    @Override
    default boolean isAuthorizedParty() {
        return true;
    }

}

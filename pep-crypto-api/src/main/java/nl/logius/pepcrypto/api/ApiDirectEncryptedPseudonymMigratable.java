package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepDirectEncryptedPseudonymMigration;
import nl.logius.pepcrypto.lib.crypto.key.PepDepMigrationClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpDecryptionPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.Function;

public interface ApiDirectEncryptedPseudonymMigratable
        extends ApiReshuffable<PepDepMigrationClosingPrivateKey, PepEpDecryptionPrivateKey> {

    @Override
    default Function<PepEcPointTriplet, ECPoint> getDecryptionFunction() {
        return PepDirectEncryptedPseudonymMigration.asFunction(getClosingPepPrivateKey(), getDecryptionPepPrivateKey());
    }

}

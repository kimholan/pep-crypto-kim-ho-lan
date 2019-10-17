package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedPseudonymDecryption;
import nl.logius.pepcrypto.lib.crypto.key.PepEpClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpDecryptionPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.Function;

public interface ApiEncryptedPseudonymDecryptable
        extends ApiReshuffable<PepEpClosingPrivateKey, PepEpDecryptionPrivateKey> {

    @Override
    default Function<PepEcPointTriplet, ECPoint> getDecryptionFunction() {
        return PepEncryptedPseudonymDecryption.asFunction(getClosingPepPrivateKey(), getDecryptionPepPrivateKey());
    }

    @Override
    default boolean isAuthorizedParty() {
        return true;
    }

}

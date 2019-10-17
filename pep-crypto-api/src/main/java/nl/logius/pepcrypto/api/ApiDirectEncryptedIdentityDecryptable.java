package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepDirectEncryptedIdentityDecryption;
import nl.logius.pepcrypto.lib.crypto.key.PepEiDecryptionPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.Function;

public interface ApiDirectEncryptedIdentityDecryptable
        extends ApiDecryptable<PepEiDecryptionPrivateKey> {

    @Override
    default Function<PepEcPointTriplet, ECPoint> getDecryptionFunction() {
        return PepDirectEncryptedIdentityDecryption.asFunction(getDecryptionPepPrivateKey());
    }

}

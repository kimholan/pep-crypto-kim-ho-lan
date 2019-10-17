package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepDirectEncryptedPseudonymDecryption;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedPseudonymDecryption;
import nl.logius.pepcrypto.lib.crypto.key.PepDrkiPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpDecryptionPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.Function;

public interface ApiDirectEncryptedPseudonymDecryptable
        extends ApiReshuffable<PepEpClosingPrivateKey, PepEpDecryptionPrivateKey> {

    @Override
    default Function<PepEcPointTriplet, ECPoint> getDecryptionFunction() {
        var decryptToEncryptedPseudonym = PepDirectEncryptedPseudonymDecryption.asFunction(getSelectedDirectReceiveKey());
        var decryptEncryptedPseudonym = PepEncryptedPseudonymDecryption.asFunction(getClosingPepPrivateKey(), getDecryptionPepPrivateKey());

        return decryptToEncryptedPseudonym.andThen(decryptEncryptedPseudonym);
    }

    PepDrkiPrivateKey getSelectedDirectReceiveKey();

}

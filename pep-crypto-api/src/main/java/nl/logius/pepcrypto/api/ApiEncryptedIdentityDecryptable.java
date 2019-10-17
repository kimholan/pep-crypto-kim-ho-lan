package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoded;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedIdentityDecryption;
import nl.logius.pepcrypto.lib.crypto.key.PepEiDecryptionPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.Function;

public interface ApiEncryptedIdentityDecryptable
        extends ApiDecryptable<PepEiDecryptionPrivateKey> {

    @Override
    default Function<PepEcPointTriplet, ECPoint> getDecryptionFunction() {
        return PepEncryptedIdentityDecryption.asFunction(getDecryptionPepPrivateKey());
    }

    @Override
    default boolean isAuthorizedParty() {
        return true;
    }

    default PepIdentityOaepDecoded getDecodedIdentity() {
        return new PepIdentityOaepDecoded(getDecryptedEcPoint());
    }

}

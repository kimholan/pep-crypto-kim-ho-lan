package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.key.PepPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.Function;

import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.DIRECT_TRANSMISSION_DECRYPTION_NOT_AUTHORIZED;

/**
 * Triplet of ECPOints decryptable to a ECPoint without a reshuffle operation.
 */
public interface ApiDecryptable<T extends PepPrivateKey> {

    default void decrypt() {
        requireAuthorizedParty();

        var encryptedEcPointTriplet = getEncryptedEcPointTriplet();
        var apiFunction = getDecryptionFunction();
        var decryptedEcPoint = apiFunction.apply(encryptedEcPointTriplet);

        setDecryptedEcPoint(decryptedEcPoint);
    }

    default void requireAuthorizedParty() {
        DIRECT_TRANSMISSION_DECRYPTION_NOT_AUTHORIZED.requireTrue(isAuthorizedParty());
    }

    default boolean isAuthorizedParty() {
        return false;
    }

    Function<PepEcPointTriplet, ECPoint> getDecryptionFunction();

    /**
     * Key selected for decryption.
     */
    T getDecryptionPepPrivateKey();

    /**
     * Encrypted EC-Points.
     */
    PepEcPointTriplet getEncryptedEcPointTriplet();

    /**
     * Decrypted EC-Point.
     */
    ECPoint getDecryptedEcPoint();

    /**
     * Decrypted EC-Point.
     */
    void setDecryptedEcPoint(ECPoint decryptedEcPoint);

}

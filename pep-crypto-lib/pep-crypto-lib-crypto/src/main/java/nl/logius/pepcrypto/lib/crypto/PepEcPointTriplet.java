package nl.logius.pepcrypto.lib.crypto;

import nl.logius.pepcrypto.lib.crypto.key.PepPrivateKey;
import org.bouncycastle.math.ec.ECPoint;

/**
 * PEP-encoded data.
 * <p>
 * El-Gamal encryption key and data is contained in points A and B. The third point contains (possibly derived)
 * public key material to verify the encryption parameters.
 * <p>
 * There are cases where the effective data for an operation is a triplet, but the input may contain more
 * EC-points.
 */
public interface PepEcPointTriplet {

    /**
     * Random key.
     */
    ECPoint getEcPointA();

    /**
     * Encoded data.
     */
    ECPoint getEcPointB();

    /**
     * Parameter verification.
     */
    ECPoint getEcPointC();

    /**
     * Reshuffle {@link PepEcPointTriplet} assuming it to be El-Gamal crypttext.
     * <p>
     * See 'The polymorphic eID scheme, version 1.11, 8 July 2019', 4.2 ElGamal encryption.
     *
     * @param privateKey Private key for reshuffle operation.
     * @return Reshuffled copy of this triplet.
     */
    default PepEcPointTriplet egReshuffle(PepPrivateKey privateKey) {
        var privateKeyValue = privateKey.getValue();
        return PepCrypto.egReshuffle(this, privateKeyValue);
    }

    /**
     * Decrypt {@link PepEcPointTriplet} assuming it to be El-Gamal crypttext.
     * <p>
     * See 'The polymorphic eID scheme, version 1.11, 8 July 2019', 4.2 ElGamal encryption.
     *
     * @param privateKey Private key for decryption.
     * @return Decrypted ECPoint.
     */
    default ECPoint egDecrypt(PepPrivateKey privateKey) {
        var privateKeyValue = privateKey.getValue();
        return PepCrypto.egDecrypt(this, privateKeyValue);
    }

}

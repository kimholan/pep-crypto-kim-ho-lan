package nl.logius.pepcrypto.lib.crypto.algorithm;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.key.PepDrkiPrivateKey;
import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.UnaryOperator;

import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.DIRECT_RECEIVE_PRIVATE_KEY_WITHIN_BOUNDS;

public enum PepDirectEncryptedPseudonymDecryption
        implements PepExceptionDetail {

    /**
     * Direct decryption failed.
     */
    DIRECT_DECRYPTION_FAILED;

    /**
     * Decrypt the Direct Encrypted-data to its regular Encrypted counterpart.
     * <p>
     * See 'The polymorphic eID scheme, version 1.11, 8 July 2019', 5.4.4 Validation and decryption
     * of Direct Encrypted Pseudonym, Algorithm 26.
     *
     * @param ecPointTriplet {@link ECPoint}-triplet containing the encrypted data.
     * @param drki           Private key, also known as DR-Di,R or DRKi .
     * @return 'Regular' Encrypted data.
     */
    public static PepEcPointTriplet depDecryption(PepEcPointTriplet ecPointTriplet, PepDrkiPrivateKey drki) {
        DIRECT_RECEIVE_PRIVATE_KEY_WITHIN_BOUNDS.requireWithinBounds(drki);

        return DIRECT_DECRYPTION_FAILED.call(() -> ecPointTriplet.egReshuffle(drki));
    }

    public static UnaryOperator<PepEcPointTriplet> asFunction(PepDrkiPrivateKey drki) {
        return it -> depDecryption(it, drki);
    }
}

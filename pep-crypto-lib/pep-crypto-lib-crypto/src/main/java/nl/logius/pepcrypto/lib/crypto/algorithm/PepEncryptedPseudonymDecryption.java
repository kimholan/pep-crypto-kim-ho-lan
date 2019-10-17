package nl.logius.pepcrypto.lib.crypto.algorithm;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.key.PepEpClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.Function;

import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.PSEUDONYM_CLOSING_PRIVATE_KEY_WITHIN_BOUNDS;
import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.PSEUDONYM_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS;

public enum PepEncryptedPseudonymDecryption
        implements PepExceptionDetail {

    /**
     * Pseudonym decryption failed.
     */
    PSEUDONYM_DECRYPTION_FAILED;

    /**
     * Decryption of Pseudonym-type data.
     * <p>
     * See 'The polymorphic eID scheme, version 1.11, 8 July 2019', 5.4.3, Algorithm 25.
     * Note that results are identical to a decryption without a closing key if the closing key has value '1'.
     *
     * @param triplet      {@link ECPoint}-triplet containing the encrypted data.
     * @param epClosing    Private key, also known as PC-Di.
     * @param epDecryption Private key, also known as PD-Di.
     * @return Reshuffled/decrypted pseudonym.
     */
    public static ECPoint epDecryption(PepEcPointTriplet triplet, PepEpClosingPrivateKey epClosing, PepEpDecryptionPrivateKey epDecryption) {
        PSEUDONYM_CLOSING_PRIVATE_KEY_WITHIN_BOUNDS.requireWithinBounds(epClosing);
        PSEUDONYM_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS.requireWithinBounds(epDecryption);

        return PSEUDONYM_DECRYPTION_FAILED.call(() -> triplet.egReshuffle(epClosing).egDecrypt(epDecryption));
    }

    public static Function<PepEcPointTriplet, ECPoint> asFunction(PepEpClosingPrivateKey epClosing, PepEpDecryptionPrivateKey epDecryption) {
        return it -> epDecryption(it, epClosing, epDecryption);
    }

}


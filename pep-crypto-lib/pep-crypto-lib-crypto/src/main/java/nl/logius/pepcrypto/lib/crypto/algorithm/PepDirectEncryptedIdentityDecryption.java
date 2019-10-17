package nl.logius.pepcrypto.lib.crypto.algorithm;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.key.PepEiDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.Function;

import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.IDENTITY_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS;

public enum PepDirectEncryptedIdentityDecryption
        implements PepExceptionDetail {

    /**
     * Identity decryption failed.
     */
    IDENTITY_DECRYPTION_FAILED;

    /**
     * Decryption of Identity-type data.
     * <p>
     * See 'The polymorphic eID scheme, version 1.11, 8 July 2019', 5.4.1 Validation and decryption
     * of a Direct Encrypted Identity, Algorithm 24.
     *
     * @param ecPointTriplet {@link ECPoint}-triplet containing the encrypted data.
     * @param eiDecryption   Private key, also known as ID-Di.
     * @return Decrypted identity.
     */
    public static ECPoint deiDecryption(PepEcPointTriplet ecPointTriplet, PepEiDecryptionPrivateKey eiDecryption) {
        IDENTITY_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS.requireWithinBounds(eiDecryption);

        return IDENTITY_DECRYPTION_FAILED.call(() -> ecPointTriplet.egDecrypt(eiDecryption));
    }

    public static Function<PepEcPointTriplet, ECPoint> asFunction(PepEiDecryptionPrivateKey eiDecryptionPrivateKey) {
        return it -> deiDecryption(it, eiDecryptionPrivateKey);
    }

}

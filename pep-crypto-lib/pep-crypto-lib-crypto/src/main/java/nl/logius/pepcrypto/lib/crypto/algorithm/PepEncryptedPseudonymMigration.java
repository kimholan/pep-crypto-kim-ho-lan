package nl.logius.pepcrypto.lib.crypto.algorithm;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.key.PepEpDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpMigrationPrivateKey;
import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.Function;

import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.EP_MIGRATION_CLOSING_PRIVATE_KEY_WITHIN_BOUNDS;
import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.PSEUDONYM_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS;

public enum PepEncryptedPseudonymMigration
        implements PepExceptionDetail {

    /**
     * Pseudonym decryption and migration in one go failed.
     */
    PSEUDONYM_DECRYPTION_AND_MIGRATION_FAILED;

    /**
     * Decryption and migration of Pseudonym-type data.
     * <p>
     * See 'The polymorphic eID scheme, version 1.11, 8 July 2019', 9, Algorithm 29,
     * steps 4-6. Do note that the results are identical to a decryption without a closing
     * key if the closing key has value '1'.
     *
     * @param ecPointTriplet     {@link ECPoint}-triplet containing the encrypted data.
     * @param epMigrationClosing Private key, also known as PC-Di.
     * @param epDecryption       Private key, also known as PD-Di.
     * @return Reshuffled/decrypted pseudonym.
     */
    public static ECPoint epMigration(PepEcPointTriplet ecPointTriplet, PepEpMigrationPrivateKey epMigrationClosing, PepEpDecryptionPrivateKey epDecryption) {
        EP_MIGRATION_CLOSING_PRIVATE_KEY_WITHIN_BOUNDS.requireWithinBounds(epMigrationClosing);
        PSEUDONYM_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS.requireWithinBounds(epDecryption);

        return PSEUDONYM_DECRYPTION_AND_MIGRATION_FAILED.call(() -> ecPointTriplet.egReshuffle(epMigrationClosing).egDecrypt(epDecryption));
    }

    public static Function<PepEcPointTriplet, ECPoint> asFunction(PepEpMigrationPrivateKey epMigrationClosing, PepEpDecryptionPrivateKey epDecryption) {
        return it -> epMigration(it, epMigrationClosing, epDecryption);
    }
}

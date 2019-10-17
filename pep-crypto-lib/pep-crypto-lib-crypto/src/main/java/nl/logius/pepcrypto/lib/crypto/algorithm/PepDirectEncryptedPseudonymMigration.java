package nl.logius.pepcrypto.lib.crypto.algorithm;

import nl.logius.pepcrypto.lib.crypto.PepEcPointTriplet;
import nl.logius.pepcrypto.lib.crypto.key.PepDepMigrationClosingPrivateKey;
import nl.logius.pepcrypto.lib.crypto.key.PepEpDecryptionPrivateKey;
import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;
import org.bouncycastle.math.ec.ECPoint;

import java.util.function.Function;

import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.EP_MIGRATION_CLOSING_PRIVATE_KEY_WITHIN_BOUNDS;
import static nl.logius.pepcrypto.lib.crypto.key.PepCryptoKeyExceptionDetail.PSEUDONYM_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS;

public enum PepDirectEncryptedPseudonymMigration
        implements PepExceptionDetail {

    DIRECT_PSEUDONYM_DECRYPTION_AND_MIGRATION_FAILED;

    /**
     * Decryption and migration of Direct Encrypted Pseudonym-type data.
     * <p>
     * See 'The polymorphic eID scheme, version 1.11, 8 July 2019', 9, Algorithm 30.
     * Note that results are identical to a decryption without a closing key if the closing key has value '1'.
     *
     * @param ecPointTriplet      {@link ECPoint}-triplet containing the encrypted data.
     * @param depMigrationClosing Derived private key value for migrating the Direct Encrypted Pseudonym.
     * @param epDecryption        PD_D private key value.
     * @return Reshuffled/decrypted pseudonym.
     */
    public static ECPoint depMigration(PepEcPointTriplet ecPointTriplet, PepDepMigrationClosingPrivateKey depMigrationClosing, PepEpDecryptionPrivateKey epDecryption) {
        EP_MIGRATION_CLOSING_PRIVATE_KEY_WITHIN_BOUNDS.requireWithinBounds(depMigrationClosing);
        PSEUDONYM_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS.requireWithinBounds(epDecryption);

        return DIRECT_PSEUDONYM_DECRYPTION_AND_MIGRATION_FAILED.call(() -> ecPointTriplet.egReshuffle(depMigrationClosing).egDecrypt(epDecryption));
    }

    public static Function<PepEcPointTriplet, ECPoint> asFunction(PepDepMigrationClosingPrivateKey depMigrationClosing, PepEpDecryptionPrivateKey epDecryption) {
        return it -> depMigration(it, depMigrationClosing, epDecryption);
    }
}

package nl.logius.pepcrypto.lib.crypto;

import java.math.BigInteger;
import java.util.Arrays;

import static java.math.BigInteger.ZERO;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.getKeyLengthByteCount;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.isBitLengthWithinBounds;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.isLessThanQb;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_PUBLIC_KEY_INFINITY;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_Q_INFINITY;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_RECIPIENT_KEY_INFINITY;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_R_BOUNDS_MAX;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_R_BOUNDS_MIN;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_S_BOUNDS_MAX;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSDSA_S_BOUNDS_MIN;
import static nl.logius.pepcrypto.lib.crypto.PepDigest.SHA384;

/**
 * Verification for EC-SDSA signatures, with OID 0.4.0.127.0.7.1.1.4.4.3
 */
public interface PepEcsdsaVerification {

    static boolean verify(byte[] signedData, PepEcSchnorrVerificationKey verificationKey, PepEcSignature signature) {
        return VERIFY_SIGNATURE_ECSDSA.call(() -> doVerify(signedData, verificationKey, signature));
    }

    private static boolean doVerify(byte[] signedData, PepEcSchnorrVerificationKey verificationkeys, PepEcSignature signature) {
        var r = signature.getR();
        VERIFY_SIGNATURE_ECSDSA_R_BOUNDS_MIN.requireTrue(r.compareTo(ZERO) > 0);
        VERIFY_SIGNATURE_ECSDSA_R_BOUNDS_MAX.requireTrue(isBitLengthWithinBounds(r));

        var s = signature.getS();
        VERIFY_SIGNATURE_ECSDSA_S_BOUNDS_MIN.requireTrue(s.compareTo(ZERO) > 0);
        VERIFY_SIGNATURE_ECSDSA_S_BOUNDS_MAX.requireTrue(isLessThanQb(s));

        var schemePublicKey = verificationkeys.getSchemePublicKey();
        VERIFY_SIGNATURE_ECSDSA_PUBLIC_KEY_INFINITY.requireFalse(schemePublicKey.isInfinity());

        var recipientPublicKey = verificationkeys.getRecipientPublicKey();
        VERIFY_SIGNATURE_ECSDSA_RECIPIENT_KEY_INFINITY.requireFalse(recipientPublicKey.isInfinity());

        var q = schemePublicKey.multiply(s).subtract(recipientPublicKey.multiply(r));
        VERIFY_SIGNATURE_ECSDSA_Q_INFINITY.requireFalse(q.isInfinity());

        var qXCoord = q.normalize().getXCoord().getEncoded();
        var qYCoord = q.normalize().getYCoord().getEncoded();
        var hash = SHA384.digest(qXCoord, qYCoord, signedData);
        var rComputed = new BigInteger(1, Arrays.copyOfRange(hash, 0, getKeyLengthByteCount()));

        return rComputed.compareTo(r) == 0;
    }

}

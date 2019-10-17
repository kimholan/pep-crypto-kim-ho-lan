package nl.logius.pepcrypto.lib.crypto;

import java.math.BigInteger;
import java.util.Arrays;

import static java.math.BigInteger.ZERO;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.getKeyLengthByteCount;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.isBitLengthWithinBounds;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.isLessThanQb;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSCHNORR;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSCHNORR_PUBLIC_KEY_INFINITY;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSCHNORR_Q_INFINITY;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSCHNORR_RECIPIENT_KEY_INFINITY;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSCHNORR_R_BOUNDS_MAX;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSCHNORR_R_BOUNDS_MIN;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSCHNORR_S_BOUNDS_MAX;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECSCHNORR_S_BOUNDS_MIN;
import static nl.logius.pepcrypto.lib.crypto.PepDigest.SHA384;

/**
 * Verification for EC-Schnorr signatures, with OID 0.4.0.127.0.7.1.1.4.3.3
 */
public interface PepEcSchnorrVerification {

    static boolean verify(byte[] signedData, PepEcSchnorrVerificationKey verificationKey, PepEcSignature signature) {
        return VERIFY_SIGNATURE_ECSCHNORR.call(() -> doVerify(signedData, verificationKey, signature));
    }

    private static boolean doVerify(byte[] signedData, PepEcSchnorrVerificationKey verificationKeys, PepEcSignature signature) {
        var r = signature.getR();

        VERIFY_SIGNATURE_ECSCHNORR_R_BOUNDS_MIN.requireTrue(r.compareTo(ZERO) > 0);
        VERIFY_SIGNATURE_ECSCHNORR_R_BOUNDS_MAX.requireTrue(isBitLengthWithinBounds(r));

        var s = signature.getS();
        VERIFY_SIGNATURE_ECSCHNORR_S_BOUNDS_MIN.requireTrue(s.compareTo(ZERO) > 0);
        VERIFY_SIGNATURE_ECSCHNORR_S_BOUNDS_MAX.requireTrue(isLessThanQb(s));

        var schemePublicKey = verificationKeys.getSchemePublicKey();
        VERIFY_SIGNATURE_ECSCHNORR_PUBLIC_KEY_INFINITY.requireFalse(schemePublicKey.isInfinity());

        var recipientPublicKey = verificationKeys.getRecipientPublicKey();
        VERIFY_SIGNATURE_ECSCHNORR_RECIPIENT_KEY_INFINITY.requireFalse(recipientPublicKey.isInfinity());

        var q = schemePublicKey.multiply(s).add(recipientPublicKey.multiply(r));
        VERIFY_SIGNATURE_ECSCHNORR_Q_INFINITY.requireFalse(q.isInfinity());

        var qXCoord = q.normalize().getXCoord().getEncoded();
        var hash = SHA384.digest(signedData, qXCoord);
        var rComputed = new BigInteger(1, Arrays.copyOfRange(hash, 0, getKeyLengthByteCount()));

        return rComputed.compareTo(r) == 0;
    }

}

package nl.logius.pepcrypto.lib.crypto;

import java.security.GeneralSecurityException;

import static nl.logius.pepcrypto.lib.crypto.PepCrypto.newPublicKey;
import static nl.logius.pepcrypto.lib.crypto.PepCrypto.newSignatureSha384WithEcdsa;
import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.VERIFY_SIGNATURE_ECDSA;

/**
 * Verification for ECDSA signatures, with OID 1.2.840.10045.4.3.3
 */
public interface PepEcdsaVerification {

    static boolean verify(byte[] signedData, PepPublicVerificationKey verificationKey, PepEcSignature signature) {
        return VERIFY_SIGNATURE_ECDSA.call(() -> doVerify(signedData, verificationKey, signature));
    }

    private static boolean doVerify(byte[] signedData, PepPublicVerificationKey pepPublicVerificationKey, PepEcSignature pepEcSignature) throws GeneralSecurityException {
        var signatureBytes = pepEcSignature.getRsBytes();
        var schemePublicKey = pepPublicVerificationKey.getSchemePublicKey();

        var publicKey = newPublicKey(schemePublicKey);
        var signature = newSignatureSha384WithEcdsa();

        signature.initVerify(publicKey);
        signature.update(signedData);

        return signature.verify(signatureBytes);
    }

}

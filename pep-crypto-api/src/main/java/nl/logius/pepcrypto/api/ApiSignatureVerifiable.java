package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.PepEcSignature;
import nl.logius.pepcrypto.lib.crypto.PepPublicVerificationKey;

/**
 * Verifiable signature.
 */
public interface ApiSignatureVerifiable<T extends PepPublicVerificationKey> {

    /**
     * Keys to perform the verfication with.
     */
    T getVerificationKeys();

    /**
     * Signature to verify.
     */
    PepEcSignature getSignature();

    /**
     * Signed data.
     */
    byte[] getSignedData();

}

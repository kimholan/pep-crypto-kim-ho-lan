package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.crypto.PepPublicVerificationKey;

/**
 * Verify the signature described by a verifiable object.
 */
public interface ApiSignatureVerifier<T extends PepPublicVerificationKey> {

    /**
     * Perform signature verification.
     **/
    void verify(ApiSignatureVerifiable<T> verifiable);

}


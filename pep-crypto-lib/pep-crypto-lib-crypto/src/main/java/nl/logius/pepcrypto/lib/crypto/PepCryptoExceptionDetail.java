package nl.logius.pepcrypto.lib.crypto;

import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;

public enum PepCryptoExceptionDetail
        implements PepExceptionDetail {

    /**
     * OAEP encoded bit length.
     */
    OAEP_ENCODED_IDENTITY_BIT_LENGTH,

    /**
     * Raw input for OAEP encoded identity.
     */
    OAEP_ENCODED_IDENTITY_RAW_INPUT,

    /**
     * Padding of OAEP-encoded identity.
     */
    OAEP_ENCODED_IDENTITY_PADDING,

    /**
     * Verify ECDSA signaure.
     */
    VERIFY_SIGNATURE_ECDSA,

    /**
     * Verify EC-Schnorr signature (Deprecated).
     */
    VERIFY_SIGNATURE_ECSCHNORR,

    /**
     * R-value not within bounds.
     */
    VERIFY_SIGNATURE_ECSCHNORR_R_BOUNDS_MIN,

    /**
     * R-value not within bounds.
     */
    VERIFY_SIGNATURE_ECSCHNORR_R_BOUNDS_MAX,

    /**
     * S-value not within bounds.
     */
    VERIFY_SIGNATURE_ECSCHNORR_S_BOUNDS_MIN,

    /**
     * S-value not within bounds.
     */
    VERIFY_SIGNATURE_ECSCHNORR_S_BOUNDS_MAX,

    /**
     * Public key may not be infinity.
     */
    VERIFY_SIGNATURE_ECSCHNORR_PUBLIC_KEY_INFINITY,

    /**
     * Recipient key may not be infinity.
     */
    VERIFY_SIGNATURE_ECSCHNORR_RECIPIENT_KEY_INFINITY,

    /**
     * Invalid verification key.
     */
    VERIFY_SIGNATURE_ECSCHNORR_Q_INFINITY,

    /**
     * Verify ECSDSA signature.
     */
    VERIFY_SIGNATURE_ECSDSA,

    /**
     * R-value not within bounds.
     */
    VERIFY_SIGNATURE_ECSDSA_R_BOUNDS_MIN,

    /**
     * R-value not within bounds.
     */
    VERIFY_SIGNATURE_ECSDSA_R_BOUNDS_MAX,

    /**
     * S-value not within bounds.
     */
    VERIFY_SIGNATURE_ECSDSA_S_BOUNDS_MIN,

    /**
     * S-value not within bounds.
     */
    VERIFY_SIGNATURE_ECSDSA_S_BOUNDS_MAX,

    /**
     * Public key may not be infinity.
     */
    VERIFY_SIGNATURE_ECSDSA_PUBLIC_KEY_INFINITY,

    /**
     * Recipient key may not be infinity.
     */
    VERIFY_SIGNATURE_ECSDSA_RECIPIENT_KEY_INFINITY,

    /**
     * Invalid verification key.
     */
    VERIFY_SIGNATURE_ECSDSA_Q_INFINITY,

    /**
     * IDDi private key value should be greater than 0 and smaller than qB.
     */
    IDENTITY_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS,

    /**
     * Identity decryption failed.
     */
    IDENTITY_DECRYPTION_FAILED,

    /**
     * Private key value for reshuffle operation should be greater than 0 and smaller than qB.
     */
    ELGAMAL_RESHUFFLE_PRIVATE_KEY_WITHIN_BOUNDS,

    /**
     * Reshuffle operation failed.
     */
    ELGAMAL_RESHUFFLE_FAILED,

    /**
     * Private key value for decrypt operation should be greater than 0 and smaller than qB.
     */
    ELGAMAL_DECRYPTION_PRIVATE_KEY_WITHIN_BOUNDS,

    /**
     * Decrypt operation failed.
     */
    ELGAMAL_DECRYPTION_FAILED,

    /**
     * Pseudonym decryption failed.
     */
    PSEUDONYM_DECRYPTION_FAILED,

    /**
     * Encode signature as DER.
     */
    SIGNATURE_AS_DER,

    /**
     * Generate ECDSA public key.
     */
    PUBLIC_KEY_ECDSA,

    /**
     * Other exceptions.
     */
    LIB_CRYPTO_MODULE

}


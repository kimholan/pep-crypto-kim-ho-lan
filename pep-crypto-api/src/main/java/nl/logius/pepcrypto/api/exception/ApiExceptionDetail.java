package nl.logius.pepcrypto.api.exception;

import nl.logius.pepcrypto.lib.lang.PepExceptionDetail;

public enum ApiExceptionDetail
        implements PepExceptionDetail {

    /**
     * No OID was found as the first element of the ASN.1-sequence.
     */
    ASN1_SEQUENCE_OID_NOT_FOUND,

    /**
     * Failed to processRawInput the ASN.1-structures to the java binding.
     */
    ASN1_MAPPING_FAILED,

    /**
     * Signature value attempted and has an invalid result.
     */
    SIGNATURE_VERIFICATION_FAILED,

    /**
     * Signature value attempted and has an invalid result.
     */
    INVALID_SIGNATURE_VALUE,

    /**
     * Sender is not authorized to send the direct encrypted data.
     */
    DIRECT_TRANSMISSION_DECRYPTION_NOT_AUTHORIZED,

    /**
     * Structure contains invalid oid.
     */
    INVALID_OID,

    /**
     * Public key does not match the expected value specified by the ECPoint-triplet.
     */
    EXPECTED_PUBLIC_KEY_MISMATCH,

    /**
     * Unspecified failure.
     */
    API_MODULE

}

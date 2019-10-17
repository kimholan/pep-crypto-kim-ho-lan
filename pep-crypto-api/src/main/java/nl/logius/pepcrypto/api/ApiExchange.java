package nl.logius.pepcrypto.api;

/**
 * Input/output parameters for the API-call.
 */
public interface ApiExchange<T> {

    /**
     * Raw input assumed to be processable DER-encoded data.
     */
    byte[] getRawInput();

    /**
     * Decoded ASN.1-data mapped to the Java binding.
     */
    T getMappedInput();

    /**
     * Object mapping to the decoded ASN.1-data.
     */
    void setMappedInput(T mappedInput);

}

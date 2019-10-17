package nl.logius.pepcrypto.api;

/**
 * Mapping of ASN.1-schema types.
 *
 * @param <T> Target ASN.1-schema type for the mapped object.
 */
public interface ApiAsn1Mapper<T> {

    /**
     * Maps the input from the exchange to a ASN.1-schema type.
     *
     * @param exchange Exchange containing the raw input to be mapped.
     */
    void processRawInput(ApiExchange<T> exchange);

}

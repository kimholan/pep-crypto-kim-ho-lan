package nl.logius.pepcrypto.api.encrypted;

import nl.logius.pepcrypto.api.ApiExchangeService;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;

/**
 * SignedDirectEncryptedPseudonym verification/decryption service.
 */
public interface ApiSignedDirectEncryptedPseudonymService
        extends ApiExchangeService<Asn1SignedDirectEncryptedPseudonymEnvelope, ApiSignedDirectEncryptedPseudonymExchange> {

}

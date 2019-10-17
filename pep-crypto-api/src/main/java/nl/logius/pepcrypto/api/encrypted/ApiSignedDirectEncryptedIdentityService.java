package nl.logius.pepcrypto.api.encrypted;

import nl.logius.pepcrypto.api.ApiExchangeService;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope;

/**
 * SignedDirectEncryptedIdentity verification/decryption service.
 */
public interface ApiSignedDirectEncryptedIdentityService
        extends ApiExchangeService<Asn1SignedDirectEncryptedIdentityv2Envelope, ApiSignedDirectEncryptedIdentityExchange> {

}

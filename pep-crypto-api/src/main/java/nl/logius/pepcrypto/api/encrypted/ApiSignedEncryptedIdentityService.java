package nl.logius.pepcrypto.api.encrypted;

import nl.logius.pepcrypto.api.ApiExchangeService;
import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope;

public interface ApiSignedEncryptedIdentityService
        extends ApiExchangeService<Asn1IdentityEnvelope, ApiSignedEncryptedIdentityExchange> {

}

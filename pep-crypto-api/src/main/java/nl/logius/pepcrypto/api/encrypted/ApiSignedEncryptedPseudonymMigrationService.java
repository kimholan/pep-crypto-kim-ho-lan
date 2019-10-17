package nl.logius.pepcrypto.api.encrypted;

import nl.logius.pepcrypto.api.ApiExchangeService;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;

public interface ApiSignedEncryptedPseudonymMigrationService
        extends ApiExchangeService<Asn1PseudonymEnvelope, ApiSignedEncryptedPseudonymMigrationExchange> {

}

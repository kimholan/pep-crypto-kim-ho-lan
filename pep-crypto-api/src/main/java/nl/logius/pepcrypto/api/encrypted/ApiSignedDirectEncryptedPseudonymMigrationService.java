package nl.logius.pepcrypto.api.encrypted;

import nl.logius.pepcrypto.api.ApiExchangeService;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;

public interface ApiSignedDirectEncryptedPseudonymMigrationService
        extends ApiExchangeService<Asn1SignedDirectEncryptedPseudonymEnvelope, ApiSignedDirectEncryptedPseudonymMigrationExchange> {

}

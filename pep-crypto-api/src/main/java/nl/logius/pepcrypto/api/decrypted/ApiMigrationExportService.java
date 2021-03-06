package nl.logius.pepcrypto.api.decrypted;

import nl.logius.pepcrypto.api.ApiExchangeService;
import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;

public interface ApiMigrationExportService
        extends ApiExchangeService<Asn1DecryptedPseudonym, ApiMigrationExportExchange> {

}

package nl.logius.pepcrypto.api.decrypted;

import nl.logius.pepcrypto.api.ApiDecryptedPseudonymExportable;
import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;

public interface ApiMigrationExportExchange
        extends ApiExchange<Asn1DecryptedPseudonym>,
                ApiDecryptedPseudonymExportable {

}


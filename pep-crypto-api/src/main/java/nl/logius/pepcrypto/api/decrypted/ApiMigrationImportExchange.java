package nl.logius.pepcrypto.api.decrypted;

import nl.logius.pepcrypto.api.ApiDecryptedPseudonymResult;
import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.api.ApiMigrationIntermediaryPseudonymImportable;
import nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym;

public interface ApiMigrationImportExchange
        extends ApiExchange<Asn1MigrationIntermediaryPseudonym>,
                ApiMigrationIntermediaryPseudonymImportable,
                ApiDecryptedPseudonymResult {

}


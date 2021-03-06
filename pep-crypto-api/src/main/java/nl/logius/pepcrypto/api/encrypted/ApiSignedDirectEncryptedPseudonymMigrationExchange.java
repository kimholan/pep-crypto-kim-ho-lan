package nl.logius.pepcrypto.api.encrypted;

import nl.logius.pepcrypto.api.ApiDecryptedPseudonymResult;
import nl.logius.pepcrypto.api.ApiDirectEncryptedPseudonymMigratable;
import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.api.ApiSignatureVerifiable;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepPublicVerificationKey;

/**
 * Input/output parameters for processing direct encrypted pseudonyms.
 */
public interface ApiSignedDirectEncryptedPseudonymMigrationExchange
        extends ApiExchange<Asn1SignedDirectEncryptedPseudonymEnvelope>,
                ApiSignatureVerifiable<PepPublicVerificationKey>,
                ApiDirectEncryptedPseudonymMigratable,
                ApiDecryptedPseudonymResult {

}

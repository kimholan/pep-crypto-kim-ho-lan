package nl.logius.pepcrypto.api.encrypted;

import nl.logius.pepcrypto.api.ApiDecryptedPseudonymResult;
import nl.logius.pepcrypto.api.ApiEncryptedPseudonymMigratable;
import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.api.ApiSignatureVerifiable;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerificationKey;

/**
 * Input/output parameters for processing encrypted pseudonyms.
 */
public interface ApiSignedEncryptedPseudonymMigrationExchange
        extends ApiExchange<Asn1PseudonymEnvelope>,
                ApiSignatureVerifiable<PepEcSchnorrVerificationKey>,
                ApiEncryptedPseudonymMigratable,
                ApiDecryptedPseudonymResult {

}

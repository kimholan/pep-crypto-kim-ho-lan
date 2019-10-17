package nl.logius.pepcrypto.api.encrypted;

import nl.logius.pepcrypto.api.ApiDecryptedPseudonymResult;
import nl.logius.pepcrypto.api.ApiDirectEncryptedPseudonymDecryptable;
import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.api.ApiSignatureVerifiable;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepPublicVerificationKey;

/**
 * Input/output parameters for processing SignedDirectEncryptedPseudonym.
 */
public interface ApiSignedDirectEncryptedPseudonymExchange
        extends ApiExchange<Asn1SignedDirectEncryptedPseudonymEnvelope>,
                ApiSignatureVerifiable<PepPublicVerificationKey>,
                ApiDirectEncryptedPseudonymDecryptable,
                ApiDecryptedPseudonymResult {

}


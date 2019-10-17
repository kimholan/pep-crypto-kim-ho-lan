package nl.logius.pepcrypto.api.encrypted;

import nl.logius.pepcrypto.api.ApiDirectEncryptedIdentityDecryptable;
import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.api.ApiSignatureVerifiable;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope;
import nl.logius.pepcrypto.lib.crypto.PepPublicVerificationKey;

/**
 * Input/output parameters for processing SignedDirectEncryptedIdentity.
 */
public interface ApiSignedDirectEncryptedIdentityExchange
        extends ApiExchange<Asn1SignedDirectEncryptedIdentityv2Envelope>,
                ApiSignatureVerifiable<PepPublicVerificationKey>,
                ApiDirectEncryptedIdentityDecryptable {

}


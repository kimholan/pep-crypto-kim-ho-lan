package nl.logius.pepcrypto.api.encrypted;

import nl.logius.pepcrypto.api.ApiEncryptedIdentityDecryptable;
import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.api.ApiSignatureVerifiable;
import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerificationKey;

/**
 * Input/output parameters for processing encrypted identities.
 */
public interface ApiSignedEncryptedIdentityExchange
        extends ApiExchange<Asn1IdentityEnvelope>,
                ApiSignatureVerifiable<PepEcSchnorrVerificationKey>,
                ApiEncryptedIdentityDecryptable {

}

package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedpseudonym;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedPseudonymResponse;
import nl.logius.pepcrypto.application.microprofile.schema.decryptedpseudonym.MicroprofileDecryptedPseudonym;
import nl.logius.pepcrypto.application.microprofile.schema.signeddirectencryptedpseudonym.MicroprofileSignedDirectEncryptedPseudonym;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.ENCODE_DECRYPTED_PSEUDONYM_AS_DER;

/**
 * Maps the processing state to the outgoing message.
 */
@ApplicationScoped
class MicroprofileSignedDirectEncryptedPseudonymResponseMapper {

    OASDecryptedPseudonymResponse mapToResponse(MicroprofileSignedDirectEncryptedPseudonymExchange exchange) {
        var response = new OASDecryptedPseudonymResponse();

        var pseudonym = exchange.asAsn1DecryptedPseudonym();
        var decodedPseudonym = MicroprofileDecryptedPseudonym.newInstance(pseudonym);
        var pseudonymEncoded = ENCODE_DECRYPTED_PSEUDONYM_AS_DER.call(pseudonym::encodeByteArray);
        var mappedInput = exchange.getMappedInput();
        var decodedInput = MicroprofileSignedDirectEncryptedPseudonym.newInstance(mappedInput);

        response.pseudonym(pseudonymEncoded)
                .decodedPseudonym(decodedPseudonym)
                .decodedInput(decodedInput);

        return response;
    }

}

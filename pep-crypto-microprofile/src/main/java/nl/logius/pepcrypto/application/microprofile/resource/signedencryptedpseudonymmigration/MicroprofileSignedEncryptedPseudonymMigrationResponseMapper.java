package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedpseudonymmigration;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedPseudonymResponse;
import nl.logius.pepcrypto.application.microprofile.schema.decryptedpseudonym.MicroprofileDecryptedPseudonym;
import nl.logius.pepcrypto.application.microprofile.schema.signedencryptedpseudonym.MicroprofileSignedEncryptedPseudonym;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.ENCODE_DECRYPTED_PSEUDONYM_AS_DER;

/**
 * Maps the processing state to the outgoing message.
 */
@ApplicationScoped
class MicroprofileSignedEncryptedPseudonymMigrationResponseMapper {

    OASDecryptedPseudonymResponse mapToResponse(MicroprofileSignedEncryptedPseudonymMigrationExchange exchange) {
        var response = new OASDecryptedPseudonymResponse();

        var pseudonym = exchange.asAsn1DecryptedPseudonym();
        var decodedPseudonym = MicroprofileDecryptedPseudonym.newInstance(pseudonym);
        var pseudonymEncoded = ENCODE_DECRYPTED_PSEUDONYM_AS_DER.call(pseudonym::encodeByteArray);
        var mappedInput = exchange.getMappedInput();
        var decodedInput = MicroprofileSignedEncryptedPseudonym.newInstance(mappedInput);

        response.pseudonym(pseudonymEncoded)
                .decodedPseudonym(decodedPseudonym)
                .decodedInput(decodedInput);

        return response;
    }

}

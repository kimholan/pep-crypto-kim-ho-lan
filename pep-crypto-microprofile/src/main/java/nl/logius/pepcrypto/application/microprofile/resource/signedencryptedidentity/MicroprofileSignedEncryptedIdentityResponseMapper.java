package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedidentity;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedIdentityResponse;
import nl.logius.pepcrypto.application.microprofile.schema.identitydecryptionresult.MicroprofileIdentityDecryptionResult;
import nl.logius.pepcrypto.application.microprofile.schema.signedencryptedidentity.MicroprofileSignedEncryptedIdentity;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;

/**
 * Maps the processing state to the outgoing message.
 */
@ApplicationScoped
class MicroprofileSignedEncryptedIdentityResponseMapper {

    private static final int NUMBER_OF_CHARS_IN_BSN = 9;

    OASDecryptedIdentityResponse mapToResponse(MicroprofileSignedEncryptedIdentityExchange exchange) {
        var response = new OASDecryptedIdentityResponse();

        // Input mapped
        var mappedInput = exchange.getMappedInput();
        var decodedInput = MicroprofileSignedEncryptedIdentity.newInstance(mappedInput);
        response.setDecodedInput(decodedInput);

        // Raw decoded identifier for debuggin purposes
        var decodedIdentity = exchange.getDecodedIdentity();
        var decryptionResult = MicroprofileIdentityDecryptionResult.newInstance(decodedIdentity);
        response.setDecryptionResult(decryptionResult);

        // Format the identifier for usability purposes
        var identifier = decryptionResult.getIdentifier();
        var bsn = StringUtils.leftPad(identifier, NUMBER_OF_CHARS_IN_BSN, '0');
        response.setBsn(bsn);

        return response;
    }

}

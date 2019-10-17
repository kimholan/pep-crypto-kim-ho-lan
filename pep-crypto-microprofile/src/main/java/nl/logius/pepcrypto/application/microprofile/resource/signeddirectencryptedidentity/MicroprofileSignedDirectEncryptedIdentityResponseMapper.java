package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedidentity;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedIdentityResponse;
import nl.logius.pepcrypto.application.microprofile.schema.identitydecryptionresult.MicroprofileIdentityDecryptionResult;
import nl.logius.pepcrypto.application.microprofile.schema.signeddirectencryptedidentity.MicroprofileSignedDirectEncryptedIdentity;
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoded;
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoder;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;

/**
 * Maps the processing state to the outgoing message.
 */
@ApplicationScoped
class MicroprofileSignedDirectEncryptedIdentityResponseMapper {

    private static final int NUMBER_OF_CHARS_IN_BSN = 9;

    OASDecryptedIdentityResponse mapToResponse(MicroprofileSignedDirectEncryptedIdentityExchange exchange) {
        var response = new OASDecryptedIdentityResponse();

        var mappedInput = exchange.getMappedInput();
        var decodedInput = MicroprofileSignedDirectEncryptedIdentity.newInstance(mappedInput);
        response.setDecodedInput(decodedInput);

        var oaepEncoded = exchange.getDecryptedEcPoint();
        var bytes = PepIdentityOaepDecoder.oaepDecode(oaepEncoded);
        var pepIdentityOaepDecoded = new PepIdentityOaepDecoded(bytes);
        var decryptionResult = MicroprofileIdentityDecryptionResult.newInstance(pepIdentityOaepDecoded);

        response.setDecryptionResult(decryptionResult);

        // Format the identifier for usability purposes
        var identifier = decryptionResult.getIdentifier();
        var bsn = StringUtils.leftPad(identifier, NUMBER_OF_CHARS_IN_BSN, '0');
        response.setBsn(bsn);

        return response;
    }

}

package nl.logius.pepcrypto.application.microprofile.resource.pseudonymconversion;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedPseudonymResponse;
import nl.logius.pepcrypto.application.microprofile.schema.decryptedpseudonym.MicroprofileDecryptedPseudonym;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.ENCODE_DECRYPTED_PSEUDONYM_AS_DER;

@ApplicationScoped
public class MicroprofilePseudonymConversionResponseMapper {

    public OASDecryptedPseudonymResponse mapToResponse(MicroprofilePseudonymConversionExchange exchange) {
        var sourcePseudonym = exchange.getMappedInput();
        var decodedInput = MicroprofileDecryptedPseudonym.newInstance(sourcePseudonym);

        // decoded pseudonym after conversion
        var targetPseudonym = exchange.asAsn1DecryptedPseudonym();
        var targetPseudonymEncoded = ENCODE_DECRYPTED_PSEUDONYM_AS_DER.call(targetPseudonym::encodeByteArray);
        var targetPseudonymDecoded = MicroprofileDecryptedPseudonym.newInstance(targetPseudonym);

        var response = new OASDecryptedPseudonymResponse();
        response.pseudonym(targetPseudonymEncoded)
                .decodedPseudonym(targetPseudonymDecoded)
                .decodedInput(decodedInput);

        return response;
    }

}

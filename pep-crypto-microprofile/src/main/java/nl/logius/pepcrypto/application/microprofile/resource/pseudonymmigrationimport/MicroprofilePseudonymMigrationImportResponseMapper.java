package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationimport;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedPseudonymResponse;
import nl.logius.pepcrypto.application.microprofile.schema.decryptedpseudonym.MicroprofileDecryptedPseudonym;
import nl.logius.pepcrypto.application.microprofile.schema.migrationintermediarypseudonym.MicroprofileMigrationIntermediaryPseudonym;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.ENCODE_DECRYPTED_PSEUDONYM_AS_DER;

@ApplicationScoped
class MicroprofilePseudonymMigrationImportResponseMapper {

    OASDecryptedPseudonymResponse mapToResponse(MicroprofilePseudonymMigrationImportExchange exchange) {
        var intermediaryPseudonym = exchange.getMappedInput();

        // Incoming MigrationIntermediaryPseudonym
        var decodedInput = MicroprofileMigrationIntermediaryPseudonym.newInstance(intermediaryPseudonym);

        // Import conversion: Pseudonym
        var targetPseudonym = exchange.asAsn1DecryptedPseudonym();
        var decodedPseudonym = MicroprofileDecryptedPseudonym.newInstance(targetPseudonym);
        var pseudonym = ENCODE_DECRYPTED_PSEUDONYM_AS_DER.call(targetPseudonym::encodeByteArray);

        // Response
        var response = new OASDecryptedPseudonymResponse();
        response.pseudonym(pseudonym)
                .decodedPseudonym(decodedPseudonym)
                .decodedInput(decodedInput);

        return response;
    }

}

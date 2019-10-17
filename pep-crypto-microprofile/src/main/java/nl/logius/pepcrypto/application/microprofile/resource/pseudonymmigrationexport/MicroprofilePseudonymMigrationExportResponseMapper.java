package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationexport;

import generated.asn1.MigrationIntermediaryPseudonym;
import generated.nl.logius.pepcrypto.openapi.model.OASMigrationIntermediaryPseudonymResponse;
import nl.logius.pepcrypto.application.microprofile.schema.decryptedpseudonym.MicroprofileDecryptedPseudonym;
import nl.logius.pepcrypto.application.microprofile.schema.migrationintermediarypseudonym.MicroprofileMigrationIntermediaryPseudonym;
import nl.logius.pepcrypto.lib.crypto.PepCrypto;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.application.microprofile.exception.MicroprofileExceptionDetail.ENCODE_MIGRATION_INTERMEDIARY_PSEUDONYM_AS_DER;

@ApplicationScoped
class MicroprofilePseudonymMigrationExportResponseMapper {

    OASMigrationIntermediaryPseudonymResponse mapToResponse(MicroprofilePseudonymMigrationExportExchange exchange) {
        var sourcePseudonym = exchange.getMappedInput();
        var decodedInput = MicroprofileDecryptedPseudonym.newInstance(sourcePseudonym);

        // Export conversion result
        var targetEcPoint = PepCrypto.encodeEcPointAsBase64(exchange.getConvertedEcPoint());
        var intermediary = MigrationIntermediaryPseudonym.builder()
                                                         .diversifier(sourcePseudonym.getDiversifier())
                                                         .schemeVersion(sourcePseudonym.getSchemeVersion())
                                                         .schemeKeySetVersion(sourcePseudonym.getSchemeKeySetVersion())
                                                         .source(sourcePseudonym.getRecipient())
                                                         .sourceKeySetVersion(sourcePseudonym.getRecipientKeySetVersion())
                                                         .target(exchange.getMigrationSourceTargetMigrant())
                                                         .targetKeySetVersion(exchange.getMigrationSourceTargetKeySetVersion())
                                                         .migrationID(exchange.getMigrationSourceMigrationId())
                                                         .type(sourcePseudonym.getType())
                                                         .pseudonymValue(targetEcPoint)
                                                         .build();
        var decodedMigrationIntermediaryPseudonym = MicroprofileMigrationIntermediaryPseudonym.newInstance(intermediary);
        var migrationIntermediaryPseudonym = ENCODE_MIGRATION_INTERMEDIARY_PSEUDONYM_AS_DER.call(intermediary::encodeByteArray);

        // Response
        var response = new OASMigrationIntermediaryPseudonymResponse();
        response.migrationIntermediaryPseudonym(migrationIntermediaryPseudonym)
                .decodedMigrationIntermediaryPseudonym(decodedMigrationIntermediaryPseudonym)
                .decodedInput(decodedInput);

        return response;
    }

}

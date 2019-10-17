package nl.logius.pepcrypto.application.microprofile.schema.migrationintermediarypseudonym;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.nl.logius.pepcrypto.openapi.model.OASMigrationIntermediaryPseudonym;
import nl.logius.pepcrypto.application.microprofile.schema.diversifier.MicroprofileDiversifier;
import nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonPropertyOrder({
        "notationIdentifier",
        "schemeVersion",
        "schemeKeySetVersion",
        "source",
        "sourceKeySetVersion",
        "target",
        "targetKeySetVersion",
        "type",
        "migrationID",
        "pseudonymValue",
        "diversifier"
})
public class MicroprofileMigrationIntermediaryPseudonym
        extends OASMigrationIntermediaryPseudonym {

    public static MicroprofileMigrationIntermediaryPseudonym newInstance(Asn1MigrationIntermediaryPseudonym source) {
        var target = new MicroprofileMigrationIntermediaryPseudonym();

        target.notationIdentifier(source.asn1Oid())
              .source(source.getSource())
              .sourceKeySetVersion(source.getSourceKeySetVersion().toString())
              .target(source.getTarget())
              .targetKeySetVersion(source.getTargetKeySetVersion().toString())
              .migrationID(source.getMigrationID())
              .type(Character.toString((char) source.getType().intValue()))
              .pseudonymValue(source.getPseudonymValue())
              .diversifier(MicroprofileDiversifier.newInstance(source.getDiversifier()))
              .schemeVersion(source.getSchemeVersion().toString())
              .schemeKeySetVersion(source.getSchemeKeySetVersion().toString())
        ;

        return target;
    }

}

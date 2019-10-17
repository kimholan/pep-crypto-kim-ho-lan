package nl.logius.pepcrypto.application.microprofile.schema.signeddirectencryptedpseudonym;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.asn1.ECPoint;
import generated.nl.logius.pepcrypto.openapi.model.OASDirectEncryptedPseudonym;
import nl.logius.pepcrypto.application.microprofile.schema.diversifier.MicroprofileDiversifier;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;

import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Extends OAS-model to processRawInput internal object model to OAS model whilst enforcing property during serialization.
 */
@JsonInclude(NON_NULL)
@JsonPropertyOrder({
        "notationIdentifier",
        "schemeVersion",
        "schemeKeySetVersion",
        "creator",
        "recipient",
        "recipientKeySetVersion",
        "type",
        "points",
        "diversifier",
        "authorizedParty"
})
class MicroprofileDirectEncryptedPseudonym
        extends OASDirectEncryptedPseudonym {

    static MicroprofileDirectEncryptedPseudonym newInstance(Asn1SignedDirectEncryptedPseudonymEnvelope source) {
        var target = new MicroprofileDirectEncryptedPseudonym();
        var points = source.asn1Body().getPoints().stream()
                           .map(ECPoint::getValue)
                           .collect(Collectors.toList());
        var pepRecipientKeyId = source.asAsn1RecipientKeyId();

        target.creator(source.asn1Body().getCreator())
              .diversifier(MicroprofileDiversifier.newInstance(source.asn1PseudonymDiversifier()))
              .points(points)
              .type(Character.toString((char) source.asn1PseudonymType().intValue()))
              .authorizedParty(source.asn1AuthorizedParty())
              .notationIdentifier(source.asn1BodyOid())
              .recipient(pepRecipientKeyId.getRecipient())
              .recipientKeySetVersion(pepRecipientKeyId.getRecipientKeySetVersion().toString())
              .schemeVersion(pepRecipientKeyId.getSchemeVersion().toString())
              .schemeKeySetVersion(pepRecipientKeyId.getSchemeKeySetVersion().toString())

        ;

        return target;
    }

}


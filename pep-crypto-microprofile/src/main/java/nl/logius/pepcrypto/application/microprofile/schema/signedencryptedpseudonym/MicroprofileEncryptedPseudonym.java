package nl.logius.pepcrypto.application.microprofile.schema.signedencryptedpseudonym;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.asn1.ECPoint;
import generated.nl.logius.pepcrypto.openapi.model.OASEncryptedPseudonym;
import nl.logius.pepcrypto.application.microprofile.schema.diversifier.MicroprofileDiversifier;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;

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
        "diversifier"
})
class MicroprofileEncryptedPseudonym
        extends OASEncryptedPseudonym {

    static MicroprofileEncryptedPseudonym newInstance(Asn1PseudonymEnvelope source) {
        var target = new MicroprofileEncryptedPseudonym();
        var points = source.asn1Body().getPoints().stream()
                           .map(ECPoint::getValue)
                           .collect(Collectors.toList());
        var pepRecipientKeyId = source.asAsn1RecipientKeyId();

        target.notationIdentifier(source.asn1BodyOid())
              .creator(source.asn1Body().getCreator())
              .type(Character.toString((char) source.asn1PseudonymType().intValue()))
              .points(points)
              .diversifier(MicroprofileDiversifier.newInstance(source.asn1PseudonymDiversifier()))
              .recipient(pepRecipientKeyId.getRecipient())
              .recipientKeySetVersion(pepRecipientKeyId.getRecipientKeySetVersion().toString())
              .schemeVersion(pepRecipientKeyId.getSchemeVersion().toString())
              .schemeKeySetVersion(pepRecipientKeyId.getSchemeKeySetVersion().toString());

        return target;
    }

}


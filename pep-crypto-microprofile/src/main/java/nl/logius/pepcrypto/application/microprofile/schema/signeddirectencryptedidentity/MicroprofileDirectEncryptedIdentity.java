package nl.logius.pepcrypto.application.microprofile.schema.signeddirectencryptedidentity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.asn1.ECPoint;
import generated.nl.logius.pepcrypto.openapi.model.OASDirectEncryptedIdentity;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope;

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
        "authorizedParty"
})
public class MicroprofileDirectEncryptedIdentity
        extends OASDirectEncryptedIdentity {

    public static MicroprofileDirectEncryptedIdentity newInstance(Asn1SignedDirectEncryptedIdentityv2Envelope source) {
        var target = new MicroprofileDirectEncryptedIdentity();
        var points = source.asn1Body().getPoints().stream()
                           .map(ECPoint::getValue)
                           .collect(Collectors.toList());
        var pepRecipientKeyId = source.asAsn1RecipientKeyId();

        target.creator(source.asn1Body().getCreator())
              .points(points)
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

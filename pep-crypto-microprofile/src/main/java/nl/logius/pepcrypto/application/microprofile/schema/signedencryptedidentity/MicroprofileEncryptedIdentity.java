package nl.logius.pepcrypto.application.microprofile.schema.signedencryptedidentity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.asn1.ECPoint;
import generated.nl.logius.pepcrypto.openapi.model.OASEncryptedIdentity;
import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope;

import java.util.stream.Collectors;

/**
 * Extends OAS-model to internal object model to OAS model whilst enforcing property during serialization.
 */
@JsonPropertyOrder({
        "notationIdentifier",
        "schemeVersion",
        "schemeKeySetVersion",
        "creator",
        "recipient",
        "recipientKeySetVersion",
        "points",
})
class MicroprofileEncryptedIdentity
        extends OASEncryptedIdentity {

    static MicroprofileEncryptedIdentity newInstance(Asn1IdentityEnvelope source) {
        var target = new MicroprofileEncryptedIdentity();
        var points = source.asn1SignedBody().asn1Body().getPoints().stream()
                           .map(ECPoint::getValue)
                           .collect(Collectors.toList());
        var pepRecipientKeyId = source.asAsn1RecipientKeyId();

        target.notationIdentifier(source.asn1BodyOid())
              .creator(source.asn1Body().getCreator())
              .points(points)
              .recipient(pepRecipientKeyId.getRecipient())
              .recipientKeySetVersion(pepRecipientKeyId.getRecipientKeySetVersion().toString())
              .schemeVersion(pepRecipientKeyId.getSchemeVersion().toString())
              .schemeKeySetVersion(pepRecipientKeyId.getSchemeKeySetVersion().toString());

        return target;
    }

}


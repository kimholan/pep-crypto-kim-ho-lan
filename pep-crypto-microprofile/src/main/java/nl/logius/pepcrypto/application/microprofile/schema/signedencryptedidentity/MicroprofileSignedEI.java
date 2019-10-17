package nl.logius.pepcrypto.application.microprofile.schema.signedencryptedidentity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEI;
import nl.logius.pepcrypto.application.microprofile.schema.extraelement.MicroprofileExtraElement;
import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope;
import nl.logius.pepcrypto.lib.asn1.signedencryptedidentityv2.Asn1SignedEncryptedIdentityv2Envelope;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * Extends OAS-model to processRawInput internal object model to OAS model whilst enforcing property during serialization.
 */
@JsonInclude(NON_EMPTY)
@JsonPropertyOrder({
        "encryptedIdentity",
        "auditElement",
        "issuanceDate",
        "extraElements"
})
class MicroprofileSignedEI
        extends OASSignedEI {

    static MicroprofileSignedEI newInstance(Asn1IdentityEnvelope source, byte[] auditElement) {
        var target = new MicroprofileSignedEI();

        target.encryptedIdentity(MicroprofileEncryptedIdentity.newInstance(source))
              .auditElement(auditElement);

        if (source instanceof Asn1SignedEncryptedIdentityv2Envelope) {
            var v2 = (Asn1SignedEncryptedIdentityv2Envelope) source;
            target.issuanceDate(v2.asn1SignedBody().getIssuanceDate())
                  .extraElements(MicroprofileExtraElement.newInstance(v2.asn1SignedBody().getExtraElements()));
        }

        return target;
    }

}

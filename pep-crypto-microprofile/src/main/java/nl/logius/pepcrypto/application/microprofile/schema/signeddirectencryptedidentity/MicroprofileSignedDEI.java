package nl.logius.pepcrypto.application.microprofile.schema.signeddirectencryptedidentity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDEI;
import nl.logius.pepcrypto.application.microprofile.schema.extraelement.MicroprofileExtraElement;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope;

import java.math.BigInteger;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Extends OAS-model to processRawInput internal object model to OAS model whilst enforcing property during serialization.
 */
@JsonInclude(NON_NULL)
@JsonPropertyOrder({
        "directEncryptedIdentity",
        "auditElement",
        "signingKeyVersion",
        "issuanceDate",
        "extraElements"
})
class MicroprofileSignedDEI
        extends OASSignedDEI {

    static MicroprofileSignedDEI newInstance(Asn1SignedDirectEncryptedIdentityv2Envelope directEncryptedIdentity, byte[] auditElement, BigInteger signingKeyVersion) {
        var target = new MicroprofileSignedDEI();

        target.auditElement(auditElement)
              .directEncryptedIdentity(MicroprofileDirectEncryptedIdentity.newInstance(directEncryptedIdentity))
              .signingKeyVersion(signingKeyVersion.toString())
              .issuanceDate(directEncryptedIdentity.asn1SignedBody().getIssuanceDate())
              .extraElements(MicroprofileExtraElement.newInstance(directEncryptedIdentity.getSignedDEI().getExtraElements()));

        return target;
    }

}

package nl.logius.pepcrypto.application.microprofile.schema.signeddirectencryptedidentity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedIdentity;
import nl.logius.pepcrypto.application.microprofile.schema.signaturevalue.MicroprofileSignatureValue;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope;

/**
 * Extends OAS-model to processRawInput internal object model to OAS model whilst enforcing property during serialization.
 */
@JsonPropertyOrder({
        "notationIdentifier",
        "signedDEI",
        "signatureValue",

})
public class MicroprofileSignedDirectEncryptedIdentity
        extends OASSignedDirectEncryptedIdentity {

    public static MicroprofileSignedDirectEncryptedIdentity newInstance(Asn1SignedDirectEncryptedIdentityv2Envelope source) {
        var auditElement = source.asn1AuditElement();
        var signingKeyVersion = source.asn1SigningKeyVersion();
        var target = new MicroprofileSignedDirectEncryptedIdentity();

        target.notationIdentifier(source.asn1Oid())
              .signedDEI(MicroprofileSignedDEI.newInstance(source, auditElement, signingKeyVersion))
              .signatureValue(MicroprofileSignatureValue.newInstance(source));

        return target;
    }

}

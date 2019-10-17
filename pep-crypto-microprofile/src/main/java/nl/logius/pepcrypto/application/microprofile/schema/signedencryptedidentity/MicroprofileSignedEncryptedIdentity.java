package nl.logius.pepcrypto.application.microprofile.schema.signedencryptedidentity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedIdentityType;
import nl.logius.pepcrypto.application.microprofile.schema.signaturevalue.MicroprofileSignatureValue;
import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope;

/**
 * Extends OAS-model to processRawInput internal object model to OAS model whilst enforcing property during serialization.
 */
@JsonPropertyOrder({
        "notationIdentifier",
        "signedEI",
        "signatureValue",
})
public class MicroprofileSignedEncryptedIdentity
        extends OASSignedEncryptedIdentityType {

    public static MicroprofileSignedEncryptedIdentity newInstance(Asn1IdentityEnvelope source) {
        var target = new MicroprofileSignedEncryptedIdentity();

        var auditElement = source.asn1AuditElement();

        target.notationIdentifier(source.asn1Oid())
              .signedEI(MicroprofileSignedEI.newInstance(source, auditElement))
              .signatureValue(MicroprofileSignatureValue.newInstance(source));

        return target;
    }

}

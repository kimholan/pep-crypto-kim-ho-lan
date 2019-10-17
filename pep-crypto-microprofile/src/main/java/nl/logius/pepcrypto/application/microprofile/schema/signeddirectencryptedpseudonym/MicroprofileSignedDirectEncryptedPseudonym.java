package nl.logius.pepcrypto.application.microprofile.schema.signeddirectencryptedpseudonym;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonym;
import nl.logius.pepcrypto.application.microprofile.schema.signaturevalue.MicroprofileSignatureValue;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;

/**
 * Extends OAS-model to processRawInput internal object model to OAS model whilst enforcing property during serialization.
 */
@JsonPropertyOrder({
        "notationIdentifier",
        "signedDEP",
        "signatureValue",
})
public class MicroprofileSignedDirectEncryptedPseudonym
        extends OASSignedDirectEncryptedPseudonym {

    public static MicroprofileSignedDirectEncryptedPseudonym newInstance(Asn1SignedDirectEncryptedPseudonymEnvelope source) {
        var auditElement = source.asn1AuditElement();
        var signingKeyVersion = source.asn1SigningKeyVersion();

        var target = new MicroprofileSignedDirectEncryptedPseudonym();
        target.notationIdentifier(source.asn1Oid())
              .signedDEP(MicroprofileSignedDEP.newInstance(source, auditElement, signingKeyVersion))
              .signatureValue(MicroprofileSignatureValue.newInstance(source));

        return target;
    }

}

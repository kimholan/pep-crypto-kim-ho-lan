package nl.logius.pepcrypto.application.microprofile.schema.signedencryptedpseudonym;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymType;
import nl.logius.pepcrypto.application.microprofile.schema.signaturevalue.MicroprofileSignatureValue;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;

/**
 * Extends OAS-model to processRawInput internal object model to OAS model whilst enforcing property during serialization.
 */
@JsonPropertyOrder({
        "notationIdentifier",
        "signedEP",
        "signatureValue",
})
public class MicroprofileSignedEncryptedPseudonym
        extends OASSignedEncryptedPseudonymType {

    public static MicroprofileSignedEncryptedPseudonym newInstance(Asn1PseudonymEnvelope source) {
        var target = new MicroprofileSignedEncryptedPseudonym();

        var auditElement = source.asn1AuditElement();

        target.notationIdentifier(source.asn1Oid())
              .signedEP(MicroprofileSignedEP.newInstance(source, auditElement))
              .signatureValue(MicroprofileSignatureValue.newInstance(source));

        return target;
    }

}

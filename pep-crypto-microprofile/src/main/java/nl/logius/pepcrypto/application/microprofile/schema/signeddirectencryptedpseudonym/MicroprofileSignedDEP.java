package nl.logius.pepcrypto.application.microprofile.schema.signeddirectencryptedpseudonym;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDEP;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;

import java.math.BigInteger;

/**
 * Extends OAS-model to processRawInput internal object model to OAS model whilst enforcing property during serialization.
 */
@JsonPropertyOrder({
        "directEncryptedPseudonym",
        "auditElement",
        "signingKeyVersion"
})
class MicroprofileSignedDEP
        extends OASSignedDEP {

    static MicroprofileSignedDEP newInstance(Asn1SignedDirectEncryptedPseudonymEnvelope directEncryptedPseudonym, byte[] auditElement, BigInteger signingKeyVersion) {
        var target = new MicroprofileSignedDEP();

        target.auditElement(auditElement)
              .directEncryptedPseudonym(MicroprofileDirectEncryptedPseudonym.newInstance(directEncryptedPseudonym))
              .signingKeyVersion(signingKeyVersion.toString());

        return target;
    }

}

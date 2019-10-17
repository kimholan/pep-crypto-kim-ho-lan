package nl.logius.pepcrypto.application.microprofile.schema.signedencryptedpseudonym;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEP;
import nl.logius.pepcrypto.application.microprofile.schema.extraelement.MicroprofileExtraElement;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;
import nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonymv2.Asn1SignedEncryptedPseudonymv2Envelope;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * Extends OAS-model to processRawInput internal object model to OAS model whilst enforcing property during serialization.
 */
@JsonInclude(NON_EMPTY)
@JsonPropertyOrder({
        "encryptedPseudonym",
        "auditElement",
        "issuanceDate",
        "extraElements"
})
class MicroprofileSignedEP
        extends OASSignedEP {

    static MicroprofileSignedEP newInstance(Asn1PseudonymEnvelope source, byte[] auditElement) {
        var target = new MicroprofileSignedEP();

        target.encryptedPseudonym(MicroprofileEncryptedPseudonym.newInstance(source))
              .auditElement(auditElement);

        if (source instanceof Asn1SignedEncryptedPseudonymv2Envelope) {
            var v2Source = (Asn1SignedEncryptedPseudonymv2Envelope) source;
            var signedEP = v2Source.getSignedEP();

            target.issuanceDate(signedEP.getIssuanceDate())
                  .extraElements(MicroprofileExtraElement.newInstance(signedEP.getExtraElements()));
        }

        return target;
    }

}

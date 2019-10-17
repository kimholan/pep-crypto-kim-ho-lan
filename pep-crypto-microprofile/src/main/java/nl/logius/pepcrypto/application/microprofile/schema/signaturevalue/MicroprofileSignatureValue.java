package nl.logius.pepcrypto.application.microprofile.schema.signaturevalue;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.nl.logius.pepcrypto.openapi.model.OASSignatureValue;
import nl.logius.pepcrypto.lib.asn1.Asn1Envelope;

/**
 * Extends OAS-model to processRawInput internal object model to OAS model whilst enforcing property during serialization.
 */
@JsonPropertyOrder({
        "signatureType",
        "r",
        "s",
})
public class MicroprofileSignatureValue
        extends OASSignatureValue {

    public static MicroprofileSignatureValue newInstance(Asn1Envelope source) {
        var target = new MicroprofileSignatureValue();

        target.signatureType(source.asn1SignatureOid())
              .r(source.asn1Signature().getR().toByteArray())
              .s(source.asn1Signature().getS().toByteArray());

        return target;
    }

}

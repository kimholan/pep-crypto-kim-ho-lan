package nl.logius.pepcrypto.application.microprofile.schema.decryptedpseudonym;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedPseudonym;
import nl.logius.pepcrypto.application.microprofile.schema.diversifier.MicroprofileDiversifier;
import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonPropertyOrder({
        "notationIdentifier",
        "schemeVersion",
        "schemeKeySetVersion",
        "recipient",
        "recipientKeySetVersion",
        "type",
        "pseudonymValue",
        "diversifier"
})
public class MicroprofileDecryptedPseudonym
        extends OASDecryptedPseudonym {

    public static MicroprofileDecryptedPseudonym newInstance(Asn1DecryptedPseudonym source) {
        return newInstance(source, source.getRecipientKeySetVersion().toString());
    }

    public static MicroprofileDecryptedPseudonym newInstance(Asn1DecryptedPseudonym source, String recipientKeySetVersion) {
        var target = new MicroprofileDecryptedPseudonym();

        target.notationIdentifier(source.asn1Oid())
              .type(Character.toString((char) source.getType().intValue()))
              .pseudonymValue(source.getPseudonymValue())
              .diversifier(MicroprofileDiversifier.newInstance(source.getDiversifier()))
              .recipient(source.getRecipient())
              .recipientKeySetVersion(recipientKeySetVersion)
              .schemeVersion(source.getSchemeVersion().toString())
              .schemeKeySetVersion(source.getSchemeKeySetVersion().toString())
        ;

        return target;
    }

}

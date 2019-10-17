package nl.logius.pepcrypto.lib.asn1;

import generated.asn1.Diversifier;
import nl.logius.pepcrypto.lib.asn1.recipientkeyid.Asn1RecipientKeyId;

import java.math.BigInteger;
import java.util.Optional;

public interface Asn1PseudonymEnvelope<T extends Asn1SignedBody>
        extends Asn1Envelope<T> {

    Asn1Pseudonym asn1Pseudonym();

    default BigInteger asn1PseudonymType() {
        return asn1Pseudonym().getType();
    }

    default Diversifier asn1PseudonymDiversifier() {
        return asn1Pseudonym().getDiversifier();
    }

    @Override
    default Asn1RecipientKeyId asAsn1RecipientKeyId() {
        var recipientKeyId = asn1Body().asAsn1RecipientKeyId();
        var diversifierString = Optional.ofNullable(asn1PseudonymDiversifier())
                                        .map(Asn1Diversifier::asn1DiversifierString)
                                        .orElse(null);
        return recipientKeyId.diversifier(diversifierString);
    }

}

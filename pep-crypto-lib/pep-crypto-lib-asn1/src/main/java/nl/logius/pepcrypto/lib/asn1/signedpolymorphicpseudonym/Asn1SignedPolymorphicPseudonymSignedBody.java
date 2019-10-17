package nl.logius.pepcrypto.lib.asn1.signedpolymorphicpseudonym;

import generated.asn1.PolymorphicPseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1Body;
import nl.logius.pepcrypto.lib.asn1.Asn1SignedBody;

public interface Asn1SignedPolymorphicPseudonymSignedBody
        extends Asn1SignedBody {

    PolymorphicPseudonym getPolymorphicPseudonym();

    @Override
    default Asn1Body asn1Body() {
        return getPolymorphicPseudonym();
    }

}

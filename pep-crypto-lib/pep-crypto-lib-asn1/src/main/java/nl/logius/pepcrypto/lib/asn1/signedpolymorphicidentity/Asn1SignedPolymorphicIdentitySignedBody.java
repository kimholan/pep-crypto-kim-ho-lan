package nl.logius.pepcrypto.lib.asn1.signedpolymorphicidentity;

import generated.asn1.PolymorphicIdentity;
import nl.logius.pepcrypto.lib.asn1.Asn1Body;
import nl.logius.pepcrypto.lib.asn1.Asn1SignedBody;

public interface Asn1SignedPolymorphicIdentitySignedBody
        extends Asn1SignedBody {

    PolymorphicIdentity getPolymorphicIdentity();

    @Override
    default Asn1Body asn1Body() {
        return getPolymorphicIdentity();
    }

}

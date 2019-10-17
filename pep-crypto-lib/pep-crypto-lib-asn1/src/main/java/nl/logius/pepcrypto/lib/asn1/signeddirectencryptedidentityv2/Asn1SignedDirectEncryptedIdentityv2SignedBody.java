package nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2;

import generated.asn1.DirectEncryptedIdentityv2;
import nl.logius.pepcrypto.lib.asn1.Asn1Body;
import nl.logius.pepcrypto.lib.asn1.Asn1SignedBodyv2;

public interface Asn1SignedDirectEncryptedIdentityv2SignedBody
        extends Asn1SignedBodyv2 {

    DirectEncryptedIdentityv2 getDirectEncryptedIdentity();

    @Override
    default Asn1Body asn1Body() {
        return getDirectEncryptedIdentity();
    }

}

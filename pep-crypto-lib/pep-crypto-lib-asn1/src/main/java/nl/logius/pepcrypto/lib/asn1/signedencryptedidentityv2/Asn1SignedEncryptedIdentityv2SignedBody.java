package nl.logius.pepcrypto.lib.asn1.signedencryptedidentityv2;

import generated.asn1.EncryptedIdentity;
import nl.logius.pepcrypto.lib.asn1.Asn1Body;
import nl.logius.pepcrypto.lib.asn1.Asn1SignedBodyv2;

public interface Asn1SignedEncryptedIdentityv2SignedBody
        extends Asn1SignedBodyv2 {

    EncryptedIdentity getEncryptedIdentity();

    @Override
    default Asn1Body asn1Body() {
        return getEncryptedIdentity();
    }

}

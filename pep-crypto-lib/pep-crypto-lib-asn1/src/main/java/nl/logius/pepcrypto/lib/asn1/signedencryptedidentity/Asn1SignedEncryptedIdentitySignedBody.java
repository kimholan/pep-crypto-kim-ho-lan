package nl.logius.pepcrypto.lib.asn1.signedencryptedidentity;

import generated.asn1.EncryptedIdentity;
import nl.logius.pepcrypto.lib.asn1.Asn1Body;
import nl.logius.pepcrypto.lib.asn1.Asn1SignedBody;

public interface Asn1SignedEncryptedIdentitySignedBody
        extends Asn1SignedBody {

    EncryptedIdentity getEncryptedIdentity();

    @Override
    default Asn1Body asn1Body() {
        return getEncryptedIdentity();
    }

}

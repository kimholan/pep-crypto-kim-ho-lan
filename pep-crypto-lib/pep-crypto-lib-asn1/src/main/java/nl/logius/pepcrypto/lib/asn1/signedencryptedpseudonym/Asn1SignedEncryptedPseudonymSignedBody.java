package nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym;

import generated.asn1.EncryptedPseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1Body;
import nl.logius.pepcrypto.lib.asn1.Asn1SignedBody;

public interface Asn1SignedEncryptedPseudonymSignedBody
        extends Asn1SignedBody {

    EncryptedPseudonym getEncryptedPseudonym();

    @Override
    default Asn1Body asn1Body() {
        return getEncryptedPseudonym();
    }

}

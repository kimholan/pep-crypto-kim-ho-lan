package nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonymv2;

import generated.asn1.EncryptedPseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1Body;
import nl.logius.pepcrypto.lib.asn1.Asn1SignedBodyv2;

public interface Asn1SignedEncryptedPseudonymv2SignedBody
        extends Asn1SignedBodyv2 {

    EncryptedPseudonym getEncryptedPseudonym();

    @Override
    default Asn1Body asn1Body() {
        return getEncryptedPseudonym();
    }

}

package nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonymv2;

import generated.asn1.DirectEncryptedPseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1Body;
import nl.logius.pepcrypto.lib.asn1.Asn1SignedBodyv2;

public interface Asn1SignedDirectEncryptedPseudonymv2SignedBody
        extends Asn1SignedBodyv2 {

    DirectEncryptedPseudonym getDirectEncryptedPseudonym();

    @Override
    default Asn1Body asn1Body() {
        return getDirectEncryptedPseudonym();
    }

}

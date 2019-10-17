package nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym;

import generated.asn1.DirectEncryptedPseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1Body;
import nl.logius.pepcrypto.lib.asn1.Asn1SignedBody;

public interface Asn1SignedDirectEncryptedPseudonymSignedBody
        extends Asn1SignedBody {

    DirectEncryptedPseudonym getDirectEncryptedPseudonym();

    @Override
    default Asn1Body asn1Body() {
        return getDirectEncryptedPseudonym();
    }

}

package nl.logius.pepcrypto.lib.asn1.decryptedpseudonym;

import generated.asn1.Pseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1PepType;
import nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym;
import nl.logius.pepcrypto.lib.asn1.Asn1RecipientKey;
import nl.logius.pepcrypto.lib.asn1.recipientkeyid.Asn1RecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import org.bouncycastle.math.ec.ECPoint;

import static nl.logius.pepcrypto.lib.asn1.Asn1ExceptionDetail.PEP_SCHEMA_ASN1_DECODE;

public interface Asn1DecryptedPseudonym
        extends Asn1RecipientKey, Asn1PepType, Asn1Pseudonym {

    static Asn1DecryptedPseudonym fromByteArray(byte[] bytes) {
        var pepPseudonym = new Pseudonym();

        PEP_SCHEMA_ASN1_DECODE.call(pepPseudonym::decodeByteArray, bytes);

        return pepPseudonym;
    }

    String getPseudonymValue();

    default ECPoint asn1PseudonymValueAsEcPoint() {
        return PepCrypto.decodeEcPoint(getPseudonymValue());
    }

    default Asn1RecipientKeyId asAsn1RecipientKeyId() {
        return new Asn1RecipientKeyId(this);
    }

}

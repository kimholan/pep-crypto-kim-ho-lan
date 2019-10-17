package nl.logius.pepcrypto.lib;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERIA5String;

import java.math.BigInteger;

import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.LIB_CRYPTO_MODULE;

public interface TestLib {

    static ASN1Sequence getAsn1Sequence(byte[] bytes) {
        return (ASN1Sequence) LIB_CRYPTO_MODULE.call(ASN1Sequence::fromByteArray, bytes);
    }

    static String getDerIA5String(ASN1Sequence sequence, int index) {
        return ((DERIA5String) sequence.getObjectAt(index)).getString();
    }

    static BigInteger getAsn1Integer(ASN1Sequence sequence, int index) {
        return ((ASN1Integer) sequence.getObjectAt(index)).getValue();
    }

    static String getAsn1ObjectIdentifier(ASN1Sequence sequence, int index) {
        return ((ASN1ObjectIdentifier) sequence.getObjectAt(index)).getId();
    }

    static byte[] getASN1OctetString(ASN1Sequence sequence, int index) {
        return ((ASN1OctetString) sequence.getObjectAt(index)).getOctets();
    }

    static ASN1Sequence getAsn1Sequence(ASN1Sequence sequence, int index) {
        return (ASN1Sequence) sequence.getObjectAt(index);
    }

}

package nl.logius.pepcrypto.lib.crypto;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.DERSequence;

import java.math.BigInteger;

import static nl.logius.pepcrypto.lib.crypto.PepCryptoExceptionDetail.SIGNATURE_AS_DER;
import static org.bouncycastle.asn1.ASN1Encoding.DER;

public interface PepEcSignature {

    BigInteger getR();

    BigInteger getS();

    default byte[] getRsBytes() {
        return SIGNATURE_AS_DER.call(() -> new DERSequence(new ASN1Encodable[]{
                        new ASN1Integer(getR()),
                        new ASN1Integer(getS()),
                }).getEncoded(DER)
        );
    }

}

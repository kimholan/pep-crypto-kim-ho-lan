package nl.logius.pepcrypto.lib.asn1;

import generated.asn1.ECSigValue;
import generated.asn1.ObjectIdentifiers;
import nl.logius.pepcrypto.lib.crypto.PepEcSignature;

import java.math.BigInteger;

public interface Asn1Signature
        extends PepEcSignature {

    ObjectIdentifiers getSignatureType();

    default Asn1SignatureValue asn1SignatureValue() {
        return getSignatureValue();
    }

    default String asn1SignatureOid() {
        return getSignatureType().getId();
    }

    ECSigValue getSignatureValue();

    @Override
    default BigInteger getR() {
        return asn1SignatureValue().getR();
    }

    @Override
    default BigInteger getS() {
        return asn1SignatureValue().getS();
    }

}

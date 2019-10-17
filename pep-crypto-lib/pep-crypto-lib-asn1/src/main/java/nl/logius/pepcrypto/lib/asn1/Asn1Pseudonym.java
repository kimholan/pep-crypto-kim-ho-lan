package nl.logius.pepcrypto.lib.asn1;

import generated.asn1.Diversifier;

import java.math.BigInteger;

import static nl.logius.pepcrypto.lib.asn1.PseudonymTypeValues.B;
import static nl.logius.pepcrypto.lib.asn1.PseudonymTypeValues.E;

public interface Asn1Pseudonym {

    BigInteger getType();

    Diversifier getDiversifier();

    default boolean asn1TypeB() {
        return B.equals(getType());
    }

    default boolean asn1NotTypeB() {
        return !asn1TypeB();
    }

    default boolean asn1TypeE() {
        return E.equals(getType());
    }

}

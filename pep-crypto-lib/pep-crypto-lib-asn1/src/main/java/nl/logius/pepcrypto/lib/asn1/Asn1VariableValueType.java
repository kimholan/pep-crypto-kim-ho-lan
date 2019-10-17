package nl.logius.pepcrypto.lib.asn1;

import java.math.BigInteger;
import java.util.Base64;

public interface Asn1VariableValueType {

    boolean isBinary();

    byte[] getBinary();

    boolean isText();

    String getText();

    boolean isNumber();

    BigInteger getNumber();

    default Object asn1Value() {
        Object value = null;

        if (isBinary()) {
            value = getBinary();
        } else if (isText()) {
            value = getText();
        } else if (isNumber()) {
            value = getNumber();
        }

        return value;
    }

    default String asn1ValueAsString() {
        String value = null;

        if (isBinary()) {
            value = "[OCTETSTRING]" + Base64.getEncoder().encodeToString(getBinary());
        } else if (isText()) {
            value = "[UTF8String]" + getText();
        } else if (isNumber()) {
            value = "[INTEGER]" + getNumber();
        }

        return value;
    }

}

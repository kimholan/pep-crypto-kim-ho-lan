package generated.asn1

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.ToString
import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1Encoding
import org.bouncycastle.asn1.ASN1Integer
import org.bouncycastle.asn1.ASN1OctetString
import org.bouncycastle.asn1.ASN1Primitive
import org.bouncycastle.asn1.ASN1StreamParser
import org.bouncycastle.asn1.ASN1String
import org.bouncycastle.asn1.DEROctetString
import org.bouncycastle.asn1.DEROctetStringParser
import org.bouncycastle.asn1.DERUTF8String

@ToString(excludes = ['rawInput'])
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class VariableValueType
        implements ASN1Encodable, nl.logius.pepcrypto.lib.asn1.Asn1VariableValueType {

    byte[] rawInput
    String text
    BigInteger number
    byte[] binary

    String getText() {
        return text
    }

    void setText(String text) {
        this.number = null
        this.binary = null
        this.text = text
    }

    boolean isText() {
        return this.text != null
    }

    BigInteger getNumber() {
        return number
    }

    void setNumber(BigInteger number) {
        this.text = null
        this.binary = null
        this.number = number
    }

    boolean isNumber() {
        return this.number != null
    }

    byte[] getBinary() {
        return binary
    }

    void setBinary(byte[] binary) {
        this.text = null
        this.number = null
        this.binary = binary
    }

    boolean isBinary() {
        return this.binary != null
    }

    @Override
    ASN1Primitive toASN1Primitive() {
        if (isText()) {
            return valueToAsn1Primitive(text)
        } else if (isNumber()) {
            return valueToAsn1Primitive(number)
        } else if (isBinary()) {
            return valueToAsn1Primitive(binary)
        }
    }

    byte[] getRawInput() {
        return rawInput
    }

    String encodeBase64() {
        return encodeByteArray().encodeBase64().toString()
    }

    byte[] encodeByteArray() {
        return toASN1Primitive().getEncoded(ASN1Encoding.DER)
    }

    VariableValueType decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def anyValue = new ASN1StreamParser(bytes).readObject()
        if (anyValue instanceof DEROctetStringParser) {
            anyValue = ((DEROctetStringParser) anyValue).loadedObject
        }

        if (anyValue instanceof DERUTF8String) {
            text = valueFromAsn1Primitive(anyValue)
        } else if (anyValue instanceof ASN1Integer) {
            number = valueFromAsn1Primitive(anyValue)
        } else if (anyValue instanceof DEROctetString) {
            binary = valueFromAsn1Primitive(anyValue)
        } else {
            throw new UnsupportedOperationException()
        }

        return target
    }

    private static ASN1Primitive valueToAsn1Primitive(Object value) {
        switch (value) {
            case String:
                return new DERUTF8String(value)
            case BigInteger:
                return new ASN1Integer(value)
            case byte[]:
                return new DEROctetString(value)
            default:
                throw new UnsupportedOperationException()
        }
    }

    private static def valueFromAsn1Primitive(ASN1Primitive asn1Primitive) {
        switch (asn1Primitive) {
            case ASN1Integer:
                return asn1Primitive.value
            case ASN1String:
                return asn1Primitive.string
            case ASN1OctetString:
                return asn1Primitive.octets
            default:
                throw new UnsupportedOperationException()
        }
    }

}

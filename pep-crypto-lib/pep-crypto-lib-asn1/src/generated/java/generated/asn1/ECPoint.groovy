package generated.asn1

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1Encoding
import org.bouncycastle.asn1.ASN1InputStream
import org.bouncycastle.asn1.ASN1Primitive
import org.bouncycastle.asn1.DEROctetString

@ToString(excludes = 'rawInput')
@Builder
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class ECPoint
        implements ASN1Encodable {

    byte[] rawInput

    byte[] value

    @Override
    ASN1Primitive toASN1Primitive() {
        return new DEROctetString(value)
    }

    String encodeBase64() {
        encodeByteArray().encodeBase64().toString()
    }

    byte[] encodeByteArray() {
        toASN1Primitive().getEncoded(ASN1Encoding.DER)
    }

    ECPoint decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def asnValue = new ASN1InputStream(bytes).readObject().loadedObject
        target.value = asnValue.octets

        return target
    }

}


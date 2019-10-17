package generated.asn1

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1EncodableVector
import org.bouncycastle.asn1.ASN1Encoding
import org.bouncycastle.asn1.ASN1Integer
import org.bouncycastle.asn1.ASN1Primitive
import org.bouncycastle.asn1.ASN1StreamParser
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.DERSequenceParser
import org.bouncycastle.asn1.InMemoryRepresentable

@ToString(excludes = ['rawInput'])
@Builder(excludes = [])
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class VerifiablePIPv2ProofOfConformityZp2
        implements ASN1Encodable {

    byte[] rawInput
    BigInteger r2
    BigInteger s2

    @Override
    ASN1Primitive toASN1Primitive() {
        def items = new ASN1EncodableVector()

        items.add(new ASN1Integer(r2))
        items.add(new ASN1Integer(s2))

        return new DERSequence(items)
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

    private static ASN1Encodable readObject(DERSequenceParser parser) {
        def object = parser.readObject()
        if (object instanceof InMemoryRepresentable) {
            object = ((InMemoryRepresentable) object).loadedObject
        }

        return object
    }

    VerifiablePIPv2ProofOfConformityZp2 decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def parser = (DERSequenceParser) new ASN1StreamParser(bytes).readObject()
        def object = readObject(parser)

        object = target.r2ReadRequired(target, parser, object)
        object = target.s2ReadRequired(target, parser, object)

        return target
    }

    ASN1Encodable r2ReadRequired(VerifiablePIPv2ProofOfConformityZp2 target, DERSequenceParser parser, ASN1Encodable object) {
        target.r2 = object.value

        return readObject(parser)
    }

    ASN1Encodable s2ReadRequired(VerifiablePIPv2ProofOfConformityZp2 target, DERSequenceParser parser, ASN1Encodable object) {
        target.s2 = object.value

        return readObject(parser)
    }

}

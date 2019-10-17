package generated.asn1

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1EncodableVector
import org.bouncycastle.asn1.ASN1Encoding
import org.bouncycastle.asn1.ASN1Primitive
import org.bouncycastle.asn1.ASN1StreamParser
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.DERSequenceParser
import org.bouncycastle.asn1.InMemoryRepresentable

@ToString(excludes = ['rawInput'])
@Builder(excludes = [])
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class VerifiablePIPProofOfConformity
        implements ASN1Encodable {

    byte[] rawInput
    ECPoint p1
    ECPoint t
    VerifiablePIPProofOfConformityZp1 zp1
    VerifiablePIPProofOfConformityZp2 zp2

    @Override
    ASN1Primitive toASN1Primitive() {
        def items = new ASN1EncodableVector()

        items.add(p1.toASN1Primitive())
        items.add(t.toASN1Primitive())
        items.add(zp1.toASN1Primitive())
        items.add(zp2.toASN1Primitive())

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

    VerifiablePIPProofOfConformity decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def parser = (DERSequenceParser) new ASN1StreamParser(bytes).readObject()
        def object = readObject(parser)

        object = target.p1ReadRequired(target, parser, object)
        object = target.tReadRequired(target, parser, object)
        object = target.zp1ReadRequired(target, parser, object)
        object = target.zp2ReadRequired(target, parser, object)

        return target
    }

    ASN1Encodable p1ReadRequired(VerifiablePIPProofOfConformity target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new ECPoint()

        value.decodeByteArray(encoded)
        target.p1 = value

        return readObject(parser)
    }

    ASN1Encodable tReadRequired(VerifiablePIPProofOfConformity target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new ECPoint()

        value.decodeByteArray(encoded)
        target.t = value

        return readObject(parser)
    }

    ASN1Encodable zp1ReadRequired(VerifiablePIPProofOfConformity target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new VerifiablePIPProofOfConformityZp1()

        value.decodeByteArray(encoded)
        target.zp1 = value

        return readObject(parser)
    }

    ASN1Encodable zp2ReadRequired(VerifiablePIPProofOfConformity target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new VerifiablePIPProofOfConformityZp2()

        value.decodeByteArray(encoded)
        target.zp2 = value

        return readObject(parser)
    }

}

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
import org.bouncycastle.asn1.DERIA5String
import org.bouncycastle.asn1.DEROctetString
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.DERSequenceParser
import org.bouncycastle.asn1.DERTaggedObject
import org.bouncycastle.asn1.InMemoryRepresentable

@ToString(excludes = ['rawInput'])
@Builder(excludes = [])
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class SignedEncryptedPseudonymv2SignedEP
        implements ASN1Encodable, nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonymv2.Asn1SignedEncryptedPseudonymv2SignedBody {

    byte[] rawInput
    EncryptedPseudonym encryptedPseudonym
    byte[] auditElement
    String issuanceDate
    ExtraElements extraElements

    @Override
    ASN1Primitive toASN1Primitive() {
        def items = new ASN1EncodableVector()

        items.add(encryptedPseudonym.toASN1Primitive())
        items.add(new DEROctetString(auditElement))
        items.add(new DERIA5String(issuanceDate))
        if (extraElements != null) items.add(new DERTaggedObject(false, 2, extraElements.toASN1Primitive()))

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

    SignedEncryptedPseudonymv2SignedEP decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def parser = (DERSequenceParser) new ASN1StreamParser(bytes).readObject()
        def object = readObject(parser)

        object = target.encryptedPseudonymReadRequired(target, parser, object)
        object = target.auditElementReadRequired(target, parser, object)
        object = target.issuanceDateReadRequired(target, parser, object)
        object = target.extraElementsReadOptionalTagged(target, parser, object)

        return target
    }

    ASN1Encodable encryptedPseudonymReadRequired(SignedEncryptedPseudonymv2SignedEP target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new EncryptedPseudonym()

        value.decodeByteArray(encoded)
        target.encryptedPseudonym = value

        return readObject(parser)
    }

    ASN1Encodable auditElementReadRequired(SignedEncryptedPseudonymv2SignedEP target, DERSequenceParser parser, ASN1Encodable object) {
        target.auditElement = object.octets

        return readObject(parser)
    }

    ASN1Encodable issuanceDateReadRequired(SignedEncryptedPseudonymv2SignedEP target, DERSequenceParser parser, ASN1Encodable object) {
        target.issuanceDate = object.string

        return readObject(parser)
    }

    ASN1Encodable extraElementsReadOptionalTagged(SignedEncryptedPseudonymv2SignedEP target, DERSequenceParser parser, ASN1Encodable object) {
        if (!(object instanceof DERTaggedObject)) return object

        DERTaggedObject taggedObject = object

        if (taggedObject.tagNo != 2) return object
        def encoded = taggedObject.object.getEncoded()
        target.extraElements = new ExtraElements().decodeByteArray(encoded)

        return readObject(parser)
    }

}

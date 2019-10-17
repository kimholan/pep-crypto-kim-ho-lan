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
import org.bouncycastle.asn1.DERIA5String
import org.bouncycastle.asn1.DEROctetString
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.DERSequenceParser
import org.bouncycastle.asn1.DERTaggedObject
import org.bouncycastle.asn1.InMemoryRepresentable

@ToString(excludes = ['rawInput'])
@Builder(excludes = [])
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class SignedDirectEncryptedIdentityv2SignedDEI
        implements ASN1Encodable, nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2SignedBody {

    byte[] rawInput
    DirectEncryptedIdentityv2 directEncryptedIdentity
    byte[] auditElement
    BigInteger signingKeyVersion
    String issuanceDate
    ExtraElements extraElements

    @Override
    ASN1Primitive toASN1Primitive() {
        def items = new ASN1EncodableVector()

        items.add(directEncryptedIdentity.toASN1Primitive())
        items.add(new DEROctetString(auditElement))
        items.add(new ASN1Integer(signingKeyVersion))
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

    SignedDirectEncryptedIdentityv2SignedDEI decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def parser = (DERSequenceParser) new ASN1StreamParser(bytes).readObject()
        def object = readObject(parser)

        object = target.directEncryptedIdentityReadRequired(target, parser, object)
        object = target.auditElementReadRequired(target, parser, object)
        object = target.signingKeyVersionReadRequired(target, parser, object)
        object = target.issuanceDateReadRequired(target, parser, object)
        object = target.extraElementsReadOptionalTagged(target, parser, object)

        return target
    }

    ASN1Encodable directEncryptedIdentityReadRequired(SignedDirectEncryptedIdentityv2SignedDEI target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new DirectEncryptedIdentityv2()

        value.decodeByteArray(encoded)
        target.directEncryptedIdentity = value

        return readObject(parser)
    }

    ASN1Encodable auditElementReadRequired(SignedDirectEncryptedIdentityv2SignedDEI target, DERSequenceParser parser, ASN1Encodable object) {
        target.auditElement = object.octets

        return readObject(parser)
    }

    ASN1Encodable signingKeyVersionReadRequired(SignedDirectEncryptedIdentityv2SignedDEI target, DERSequenceParser parser, ASN1Encodable object) {
        target.signingKeyVersion = object.value

        return readObject(parser)
    }

    ASN1Encodable issuanceDateReadRequired(SignedDirectEncryptedIdentityv2SignedDEI target, DERSequenceParser parser, ASN1Encodable object) {
        target.issuanceDate = object.string

        return readObject(parser)
    }

    ASN1Encodable extraElementsReadOptionalTagged(SignedDirectEncryptedIdentityv2SignedDEI target, DERSequenceParser parser, ASN1Encodable object) {
        if (!(object instanceof DERTaggedObject)) return object

        DERTaggedObject taggedObject = object

        if (taggedObject.tagNo != 2) return object
        def encoded = taggedObject.object.getEncoded()
        target.extraElements = new ExtraElements().decodeByteArray(encoded)

        return readObject(parser)
    }

}

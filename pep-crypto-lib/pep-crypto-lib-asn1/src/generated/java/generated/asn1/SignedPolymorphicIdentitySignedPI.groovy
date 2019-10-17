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
import org.bouncycastle.asn1.DEROctetString
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.DERSequenceParser
import org.bouncycastle.asn1.InMemoryRepresentable

@ToString(excludes = ['rawInput'])
@Builder(excludes = [])
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class SignedPolymorphicIdentitySignedPI
        implements ASN1Encodable, nl.logius.pepcrypto.lib.asn1.signedpolymorphicidentity.Asn1SignedPolymorphicIdentitySignedBody {

    byte[] rawInput
    PolymorphicIdentity polymorphicIdentity
    byte[] auditElement
    BigInteger signingKeyVersion

    @Override
    ASN1Primitive toASN1Primitive() {
        def items = new ASN1EncodableVector()

        items.add(polymorphicIdentity.toASN1Primitive())
        items.add(new DEROctetString(auditElement))
        items.add(new ASN1Integer(signingKeyVersion))

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

    SignedPolymorphicIdentitySignedPI decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def parser = (DERSequenceParser) new ASN1StreamParser(bytes).readObject()
        def object = readObject(parser)

        object = target.polymorphicIdentityReadRequired(target, parser, object)
        object = target.auditElementReadRequired(target, parser, object)
        object = target.signingKeyVersionReadRequired(target, parser, object)

        return target
    }

    ASN1Encodable polymorphicIdentityReadRequired(SignedPolymorphicIdentitySignedPI target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new PolymorphicIdentity()

        value.decodeByteArray(encoded)
        target.polymorphicIdentity = value

        return readObject(parser)
    }

    ASN1Encodable auditElementReadRequired(SignedPolymorphicIdentitySignedPI target, DERSequenceParser parser, ASN1Encodable object) {
        target.auditElement = object.octets

        return readObject(parser)
    }

    ASN1Encodable signingKeyVersionReadRequired(SignedPolymorphicIdentitySignedPI target, DERSequenceParser parser, ASN1Encodable object) {
        target.signingKeyVersion = object.value

        return readObject(parser)
    }

}

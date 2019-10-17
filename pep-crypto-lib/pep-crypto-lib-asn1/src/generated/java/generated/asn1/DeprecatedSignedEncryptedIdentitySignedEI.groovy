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
import org.bouncycastle.asn1.DEROctetString
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.DERSequenceParser
import org.bouncycastle.asn1.InMemoryRepresentable

@ToString(excludes = ['rawInput'])
@Builder(excludes = [])
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class DeprecatedSignedEncryptedIdentitySignedEI
        implements ASN1Encodable, nl.logius.pepcrypto.lib.asn1.signedencryptedidentity.Asn1SignedEncryptedIdentitySignedBody {

    byte[] rawInput
    EncryptedIdentity encryptedIdentity
    byte[] auditElement

    @Override
    ASN1Primitive toASN1Primitive() {
        def items = new ASN1EncodableVector()

        items.add(encryptedIdentity.toASN1Primitive())
        items.add(new DEROctetString(auditElement))

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

    DeprecatedSignedEncryptedIdentitySignedEI decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def parser = (DERSequenceParser) new ASN1StreamParser(bytes).readObject()
        def object = readObject(parser)

        object = target.encryptedIdentityReadRequired(target, parser, object)
        object = target.auditElementReadRequired(target, parser, object)

        return target
    }

    ASN1Encodable encryptedIdentityReadRequired(DeprecatedSignedEncryptedIdentitySignedEI target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new EncryptedIdentity()

        value.decodeByteArray(encoded)
        target.encryptedIdentity = value

        return readObject(parser)
    }

    ASN1Encodable auditElementReadRequired(DeprecatedSignedEncryptedIdentitySignedEI target, DERSequenceParser parser, ASN1Encodable object) {
        target.auditElement = object.octets

        return readObject(parser)
    }

}

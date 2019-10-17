package generated.asn1

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1EncodableVector
import org.bouncycastle.asn1.ASN1Encoding
import org.bouncycastle.asn1.ASN1ParsingException
import org.bouncycastle.asn1.ASN1Primitive
import org.bouncycastle.asn1.ASN1StreamParser
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.DERSequenceParser
import org.bouncycastle.asn1.InMemoryRepresentable

@ToString(excludes = ['rawInput'])
@Builder(excludes = ['notationIdentifier'])
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class SignedDirectEncryptedPseudonymv2
        implements ASN1Encodable, nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonymv2.Asn1SignedDirectEncryptedPseudonymv2Envelope {

    byte[] rawInput
    final ObjectIdentifiers notationIdentifier = ObjectIdentifiers.ID_BSNK_ENCRYPTED_DIRECT_PSEUDONYM_SIGNED_V2
    SignedDirectEncryptedPseudonymv2SignedDEP signedDEP
    ECDSASignature signatureValue

    @Override
    ASN1Primitive toASN1Primitive() {
        def items = new ASN1EncodableVector()

        items.add(notationIdentifier.toASN1Primitive())
        items.add(signedDEP.toASN1Primitive())
        items.add(signatureValue.toASN1Primitive())

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

    SignedDirectEncryptedPseudonymv2 decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def parser = (DERSequenceParser) new ASN1StreamParser(bytes).readObject()
        def object = readObject(parser)

        object = target.notationIdentifierReadRequired(target, parser, object)
        object = target.signedDEPReadRequired(target, parser, object)
        object = target.signatureValueReadRequired(target, parser, object)

        return target
    }

    ASN1Encodable notationIdentifierReadRequired(SignedDirectEncryptedPseudonymv2 target, DERSequenceParser parser, ASN1Encodable object) {
        final ObjectIdentifiers notationIdentifier = ObjectIdentifiers.ID_BSNK_ENCRYPTED_DIRECT_PSEUDONYM_SIGNED_V2

        if (!ObjectIdentifiers.ID_BSNK_ENCRYPTED_DIRECT_PSEUDONYM_SIGNED_V2.toASN1Primitive().equals(object)) {
            throw new ASN1ParsingException("Expected object identifier: " + ObjectIdentifiers.ID_BSNK_ENCRYPTED_DIRECT_PSEUDONYM_SIGNED_V2 + ", actual value " + object)
        }

        return readObject(parser)
    }

    ASN1Encodable signedDEPReadRequired(SignedDirectEncryptedPseudonymv2 target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new SignedDirectEncryptedPseudonymv2SignedDEP()

        value.decodeByteArray(encoded)
        target.signedDEP = value

        return readObject(parser)
    }

    ASN1Encodable signatureValueReadRequired(SignedDirectEncryptedPseudonymv2 target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new ECDSASignature()

        value.decodeByteArray(encoded)
        target.signatureValue = value

        return readObject(parser)
    }

}

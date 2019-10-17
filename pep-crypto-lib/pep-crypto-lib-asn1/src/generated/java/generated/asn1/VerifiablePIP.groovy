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
class VerifiablePIP
        implements ASN1Encodable {

    byte[] rawInput
    final ObjectIdentifiers notationIdentifier = ObjectIdentifiers.ID_BSNK_POLYMORPHIC_PIP_VERIFIABLE
    SignedPIP signedPIP
    VerifiablePIPProofOfConformity proofOfConformity

    @Override
    ASN1Primitive toASN1Primitive() {
        def items = new ASN1EncodableVector()

        items.add(notationIdentifier.toASN1Primitive())
        items.add(signedPIP.toASN1Primitive())
        items.add(proofOfConformity.toASN1Primitive())

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

    VerifiablePIP decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def parser = (DERSequenceParser) new ASN1StreamParser(bytes).readObject()
        def object = readObject(parser)

        object = target.notationIdentifierReadRequired(target, parser, object)
        object = target.signedPIPReadRequired(target, parser, object)
        object = target.proofOfConformityReadRequired(target, parser, object)

        return target
    }

    ASN1Encodable notationIdentifierReadRequired(VerifiablePIP target, DERSequenceParser parser, ASN1Encodable object) {
        final ObjectIdentifiers notationIdentifier = ObjectIdentifiers.ID_BSNK_POLYMORPHIC_PIP_VERIFIABLE

        if (!ObjectIdentifiers.ID_BSNK_POLYMORPHIC_PIP_VERIFIABLE.toASN1Primitive().equals(object)) {
            throw new ASN1ParsingException("Expected object identifier: " + ObjectIdentifiers.ID_BSNK_POLYMORPHIC_PIP_VERIFIABLE + ", actual value " + object)
        }

        return readObject(parser)
    }

    ASN1Encodable signedPIPReadRequired(VerifiablePIP target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new SignedPIP()

        value.decodeByteArray(encoded)
        target.signedPIP = value

        return readObject(parser)
    }

    ASN1Encodable proofOfConformityReadRequired(VerifiablePIP target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new VerifiablePIPProofOfConformity()

        value.decodeByteArray(encoded)
        target.proofOfConformity = value

        return readObject(parser)
    }

}

package generated.asn1

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1EncodableVector
import org.bouncycastle.asn1.ASN1Encoding
import org.bouncycastle.asn1.ASN1Integer
import org.bouncycastle.asn1.ASN1ParsingException
import org.bouncycastle.asn1.ASN1Primitive
import org.bouncycastle.asn1.ASN1StreamParser
import org.bouncycastle.asn1.DERIA5String
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.DERSequenceParser
import org.bouncycastle.asn1.DERTaggedObject
import org.bouncycastle.asn1.InMemoryRepresentable

@ToString(excludes = ['rawInput'])
@Builder(excludes = ['notationIdentifier'])
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class EncryptedPseudonym
        implements ASN1Encodable, nl.logius.pepcrypto.lib.asn1.Asn1Body, nl.logius.pepcrypto.lib.asn1.Asn1Pseudonym {

    byte[] rawInput
    final ObjectIdentifiers notationIdentifier = ObjectIdentifiers.ID_BSNK_ENCRYPTED_PSEUDONYM
    BigInteger schemeVersion
    BigInteger schemeKeySetVersion
    String creator
    String recipient
    BigInteger recipientKeySetVersion
    BigInteger type
    List<ECPoint> points
    Diversifier diversifier

    @Override
    ASN1Primitive toASN1Primitive() {
        def items = new ASN1EncodableVector()

        items.add(notationIdentifier.toASN1Primitive())
        items.add(new ASN1Integer(schemeVersion))
        items.add(new ASN1Integer(schemeKeySetVersion))
        items.add(new DERIA5String(creator))
        items.add(new DERIA5String(recipient))
        items.add(new ASN1Integer(recipientKeySetVersion))
        items.add(new ASN1Integer(type))
        items.add(new DERSequence(points.collect { it.toASN1Primitive() } as ASN1Encodable[]))
        if (diversifier != null) items.add(new DERTaggedObject(false, 0, diversifier.toASN1Primitive()))

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

    EncryptedPseudonym decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def parser = (DERSequenceParser) new ASN1StreamParser(bytes).readObject()
        def object = readObject(parser)

        object = target.notationIdentifierReadRequired(target, parser, object)
        object = target.schemeVersionReadRequired(target, parser, object)
        object = target.schemeKeySetVersionReadRequired(target, parser, object)
        object = target.creatorReadRequired(target, parser, object)
        object = target.recipientReadRequired(target, parser, object)
        object = target.recipientKeySetVersionReadRequired(target, parser, object)
        object = target.typeReadRequired(target, parser, object)
        object = target.pointsReadRequired(target, parser, object)
        object = target.diversifierReadOptionalTagged(target, parser, object)

        return target
    }

    ASN1Encodable notationIdentifierReadRequired(EncryptedPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        final ObjectIdentifiers notationIdentifier = ObjectIdentifiers.ID_BSNK_ENCRYPTED_PSEUDONYM

        if (!ObjectIdentifiers.ID_BSNK_ENCRYPTED_PSEUDONYM.toASN1Primitive().equals(object)) {
            throw new ASN1ParsingException("Expected object identifier: " + ObjectIdentifiers.ID_BSNK_ENCRYPTED_PSEUDONYM + ", actual value " + object)
        }

        return readObject(parser)
    }

    ASN1Encodable schemeVersionReadRequired(EncryptedPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.schemeVersion = object.value

        return readObject(parser)
    }

    ASN1Encodable schemeKeySetVersionReadRequired(EncryptedPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.schemeKeySetVersion = object.value

        return readObject(parser)
    }

    ASN1Encodable creatorReadRequired(EncryptedPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.creator = object.string

        return readObject(parser)
    }

    ASN1Encodable recipientReadRequired(EncryptedPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.recipient = object.string

        return readObject(parser)
    }

    ASN1Encodable recipientKeySetVersionReadRequired(EncryptedPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.recipientKeySetVersion = object.value

        return readObject(parser)
    }

    ASN1Encodable typeReadRequired(EncryptedPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.type = object.value

        return readObject(parser)
    }

    ASN1Encodable pointsReadRequired(EncryptedPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.points = new ArrayList<ECPoint>()
        def listSequence = (DERSequence) object

        listSequence.each {
            def itemBytes = it.encoded
            def itemValue = new ECPoint()

            itemValue.decodeByteArray(itemBytes)
            target.points.add(itemValue)
        }

        return readObject(parser)
    }

    ASN1Encodable diversifierReadOptionalTagged(EncryptedPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        if (!(object instanceof DERTaggedObject)) return object

        DERTaggedObject taggedObject = object

        if (taggedObject.tagNo != 0) return object
        def encoded = taggedObject.object.getEncoded()
        target.diversifier = new Diversifier().decodeByteArray(encoded)

        return readObject(parser)
    }

}

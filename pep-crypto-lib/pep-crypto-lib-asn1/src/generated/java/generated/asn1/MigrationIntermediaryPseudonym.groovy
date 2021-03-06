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
class MigrationIntermediaryPseudonym
        implements ASN1Encodable, nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym {

    byte[] rawInput
    final ObjectIdentifiers notationIdentifier = ObjectIdentifiers.ID_BSNK_DECRYPTED_MIGRATIONPSEUDONYM
    BigInteger schemeVersion
    BigInteger schemeKeySetVersion
    String source
    BigInteger sourceKeySetVersion
    String target
    BigInteger targetKeySetVersion
    String migrationID
    BigInteger type
    String pseudonymValue
    Diversifier diversifier

    @Override
    ASN1Primitive toASN1Primitive() {
        def items = new ASN1EncodableVector()

        items.add(notationIdentifier.toASN1Primitive())
        items.add(new ASN1Integer(schemeVersion))
        items.add(new ASN1Integer(schemeKeySetVersion))
        items.add(new DERIA5String(source))
        items.add(new ASN1Integer(sourceKeySetVersion))
        items.add(new DERIA5String(target))
        items.add(new ASN1Integer(targetKeySetVersion))
        items.add(new DERIA5String(migrationID))
        items.add(new ASN1Integer(type))
        items.add(new DERIA5String(pseudonymValue))
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

    MigrationIntermediaryPseudonym decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def parser = (DERSequenceParser) new ASN1StreamParser(bytes).readObject()
        def object = readObject(parser)

        object = target.notationIdentifierReadRequired(target, parser, object)
        object = target.schemeVersionReadRequired(target, parser, object)
        object = target.schemeKeySetVersionReadRequired(target, parser, object)
        object = target.sourceReadRequired(target, parser, object)
        object = target.sourceKeySetVersionReadRequired(target, parser, object)
        object = target.targetReadRequired(target, parser, object)
        object = target.targetKeySetVersionReadRequired(target, parser, object)
        object = target.migrationIDReadRequired(target, parser, object)
        object = target.typeReadRequired(target, parser, object)
        object = target.pseudonymValueReadRequired(target, parser, object)
        object = target.diversifierReadOptionalTagged(target, parser, object)

        return target
    }

    ASN1Encodable notationIdentifierReadRequired(MigrationIntermediaryPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        final ObjectIdentifiers notationIdentifier = ObjectIdentifiers.ID_BSNK_DECRYPTED_MIGRATIONPSEUDONYM

        if (!ObjectIdentifiers.ID_BSNK_DECRYPTED_MIGRATIONPSEUDONYM.toASN1Primitive().equals(object)) {
            throw new ASN1ParsingException("Expected object identifier: " + ObjectIdentifiers.ID_BSNK_DECRYPTED_MIGRATIONPSEUDONYM + ", actual value " + object)
        }

        return readObject(parser)
    }

    ASN1Encodable schemeVersionReadRequired(MigrationIntermediaryPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.schemeVersion = object.value

        return readObject(parser)
    }

    ASN1Encodable schemeKeySetVersionReadRequired(MigrationIntermediaryPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.schemeKeySetVersion = object.value

        return readObject(parser)
    }

    ASN1Encodable sourceReadRequired(MigrationIntermediaryPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.source = object.string

        return readObject(parser)
    }

    ASN1Encodable sourceKeySetVersionReadRequired(MigrationIntermediaryPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.sourceKeySetVersion = object.value

        return readObject(parser)
    }

    ASN1Encodable targetReadRequired(MigrationIntermediaryPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.target = object.string

        return readObject(parser)
    }

    ASN1Encodable targetKeySetVersionReadRequired(MigrationIntermediaryPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.targetKeySetVersion = object.value

        return readObject(parser)
    }

    ASN1Encodable migrationIDReadRequired(MigrationIntermediaryPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.migrationID = object.string

        return readObject(parser)
    }

    ASN1Encodable typeReadRequired(MigrationIntermediaryPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.type = object.value

        return readObject(parser)
    }

    ASN1Encodable pseudonymValueReadRequired(MigrationIntermediaryPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        target.pseudonymValue = object.string

        return readObject(parser)
    }

    ASN1Encodable diversifierReadOptionalTagged(MigrationIntermediaryPseudonym target, DERSequenceParser parser, ASN1Encodable object) {
        if (!(object instanceof DERTaggedObject)) return object

        DERTaggedObject taggedObject = object

        if (taggedObject.tagNo != 0) return object
        def encoded = taggedObject.object.getEncoded()
        target.diversifier = new Diversifier().decodeByteArray(encoded)

        return readObject(parser)
    }

}

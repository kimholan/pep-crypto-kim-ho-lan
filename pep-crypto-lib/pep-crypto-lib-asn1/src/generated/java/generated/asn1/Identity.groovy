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
import org.bouncycastle.asn1.InMemoryRepresentable

@ToString(excludes = ['rawInput'])
@Builder(excludes = ['notationIdentifier'])
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class Identity
        implements ASN1Encodable {

    byte[] rawInput
    final ObjectIdentifiers notationIdentifier = ObjectIdentifiers.ID_BSNK_DECRYPTED_IDENTIFIER
    BigInteger schemeVersion
    BigInteger schemeKeySetVersion
    String recipient
    BigInteger type
    String identityValue

    @Override
    ASN1Primitive toASN1Primitive() {
        def items = new ASN1EncodableVector()

        items.add(notationIdentifier.toASN1Primitive())
        items.add(new ASN1Integer(schemeVersion))
        items.add(new ASN1Integer(schemeKeySetVersion))
        items.add(new DERIA5String(recipient))
        items.add(new ASN1Integer(type))
        items.add(new DERIA5String(identityValue))

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

    Identity decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def parser = (DERSequenceParser) new ASN1StreamParser(bytes).readObject()
        def object = readObject(parser)

        object = target.notationIdentifierReadRequired(target, parser, object)
        object = target.schemeVersionReadRequired(target, parser, object)
        object = target.schemeKeySetVersionReadRequired(target, parser, object)
        object = target.recipientReadRequired(target, parser, object)
        object = target.typeReadRequired(target, parser, object)
        object = target.identityValueReadRequired(target, parser, object)

        return target
    }

    ASN1Encodable notationIdentifierReadRequired(Identity target, DERSequenceParser parser, ASN1Encodable object) {
        final ObjectIdentifiers notationIdentifier = ObjectIdentifiers.ID_BSNK_DECRYPTED_IDENTIFIER

        if (!ObjectIdentifiers.ID_BSNK_DECRYPTED_IDENTIFIER.toASN1Primitive().equals(object)) {
            throw new ASN1ParsingException("Expected object identifier: " + ObjectIdentifiers.ID_BSNK_DECRYPTED_IDENTIFIER + ", actual value " + object)
        }

        return readObject(parser)
    }

    ASN1Encodable schemeVersionReadRequired(Identity target, DERSequenceParser parser, ASN1Encodable object) {
        target.schemeVersion = object.value

        return readObject(parser)
    }

    ASN1Encodable schemeKeySetVersionReadRequired(Identity target, DERSequenceParser parser, ASN1Encodable object) {
        target.schemeKeySetVersion = object.value

        return readObject(parser)
    }

    ASN1Encodable recipientReadRequired(Identity target, DERSequenceParser parser, ASN1Encodable object) {
        target.recipient = object.string

        return readObject(parser)
    }

    ASN1Encodable typeReadRequired(Identity target, DERSequenceParser parser, ASN1Encodable object) {
        target.type = object.value

        return readObject(parser)
    }

    ASN1Encodable identityValueReadRequired(Identity target, DERSequenceParser parser, ASN1Encodable object) {
        target.identityValue = object.string

        return readObject(parser)
    }

}

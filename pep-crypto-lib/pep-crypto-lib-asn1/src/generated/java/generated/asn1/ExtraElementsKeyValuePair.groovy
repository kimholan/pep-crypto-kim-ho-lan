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
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.DERSequenceParser
import org.bouncycastle.asn1.InMemoryRepresentable

@ToString(excludes = ['rawInput'])
@Builder(excludes = [])
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class ExtraElementsKeyValuePair
        implements ASN1Encodable {

    byte[] rawInput
    String key
    VariableValueType value

    @Override
    ASN1Primitive toASN1Primitive() {
        def items = new ASN1EncodableVector()

        items.add(new DERIA5String(key))
        items.add(value.toASN1Primitive())

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

    ExtraElementsKeyValuePair decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        def parser = (DERSequenceParser) new ASN1StreamParser(bytes).readObject()
        def object = readObject(parser)

        object = target.keyReadRequired(target, parser, object)
        object = target.valueReadRequired(target, parser, object)

        return target
    }

    ASN1Encodable keyReadRequired(ExtraElementsKeyValuePair target, DERSequenceParser parser, ASN1Encodable object) {
        target.key = object.string

        return readObject(parser)
    }

    ASN1Encodable valueReadRequired(ExtraElementsKeyValuePair target, DERSequenceParser parser, ASN1Encodable object) {
        def encoded = object.encoded
        def value = new VariableValueType()

        value.decodeByteArray(encoded)
        target.value = value

        return readObject(parser)
    }

}

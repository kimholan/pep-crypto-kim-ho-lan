package generated.asn1

import groovy.transform.AutoClone
import groovy.transform.AutoCloneStyle
import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1Primitive
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.InMemoryRepresentable

@ToString(excludes = 'rawInput')
@Builder
@AutoClone(style = AutoCloneStyle.COPY_CONSTRUCTOR)
class ExtraElements
        implements ASN1Encodable, nl.logius.pepcrypto.lib.asn1.Asn1ExtraElements {

    byte[] rawInput
    List<ExtraElementsKeyValuePair> extraelementskeyvaluepair

    @Override
    ASN1Primitive toASN1Primitive() {
        return new DERSequence(extraelementskeyvaluepair.collect { it.toASN1Primitive() } as ASN1Encodable[])
    }

    private static ASN1Encodable readObject(def parser) {
        def object = parser.readObject()
        if (object instanceof InMemoryRepresentable) {
            object = object.loadedObject
        }

        return object
    }

    ExtraElements decodeByteArray(byte[] bytes) {
        def target = this
        target.rawInput = bytes

        List<ExtraElementsKeyValuePair> set = new ArrayList<>()
        target.extraelementskeyvaluepair = set

        def parser = new org.bouncycastle.asn1.ASN1StreamParser(bytes)
        def object = readObject(parser)
        def list = object.toList()

        if (list[0] instanceof DERSequence) {
            list.each { item ->
                def itemBytes = item.encoded
                def setItem = new ExtraElementsKeyValuePair().decodeByteArray(itemBytes)

                set.add(setItem)
            }
        } else {
            def itemBytes = object.encoded
            def setItem = new ExtraElementsKeyValuePair().decodeByteArray(itemBytes)

            set.add(setItem)
        }

        return target
    }

}

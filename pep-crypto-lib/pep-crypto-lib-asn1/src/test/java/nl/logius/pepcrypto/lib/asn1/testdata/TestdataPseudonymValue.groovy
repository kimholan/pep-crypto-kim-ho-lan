package nl.logius.pepcrypto.lib.asn1.testdata

import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym
import org.bouncycastle.asn1.ASN1Sequence

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class TestdataPseudonymValue {

    static Map pseudonymValue(File file, byte[] fileBytes) {
        def pseudonymValue = fileBytes.encodeBase64() as String
        def item = [
                fileName              : file.name,
                oid                   : '',
                schemeVersion         : '',
                schemeKeySetVersion   : '',
                recipient             : '',
                recipientKeySetVersion: '',
                type                  : '',
                pseudonymValue        : pseudonymValue as String,
        ]


        // Verify base64 representation of ECPOint
        def txt = new File(file.canonicalPath.replaceFirst('.bin', '.txt')).text.trim()
        assertEquals("TXT does not match BIN", pseudonymValue, txt)

        // Verify HEX representation of ECPOint
        def hex = new File(file.canonicalPath.replaceFirst('.bin', '.hex')).text.trim()
        assertEquals("HEX does not match BIN", fileBytes.encodeHex().toString().toUpperCase(), hex.toUpperCase())

        verifyPseudonym(file)


        def asn1File = new File(file.canonicalPath.replaceFirst('.bin', '.asn1'))
        if (asn1File.exists()) {
            def asn1Bytes = asn1File.bytes
            def asn1Object = ASN1Sequence.fromByteArray(asn1Bytes)
            def oid = asn1Object.getObjectAt(0) as String
            def asn1

            switch (oid) {
                case '2.16.528.1.1003.10.1.3.2':
                    item << [basicType: 'P']
                    asn1 = Asn1DecryptedPseudonym.fromByteArray(asn1Bytes)
                    break
                default:
                    throw new RuntimeException("working on ${file.canonicalPath}:")
            }

            assertEquals("ASN1 does not match BIN", item.pseudonymValue, asn1.pseudonymValue)

            item << [
                    oid                   : asn1.asn1Oid(),
                    schemeVersion         : asn1.schemeVersion as String,
                    schemeKeySetVersion   : asn1.schemeKeySetVersion as String,
                    recipient             : asn1.recipient as String,
                    recipientKeySetVersion: asn1.recipientKeySetVersion as String,
                    type                  : asn1.type.intValue() as char,
                    pseudonymValue        : asn1.pseudonymValue as String,
            ]

            // Add additional fields if present
            if (asn1.hasProperty('diversifier')) {
                item << [diversifier: asn1.diversifier?.diversifierkeyvaluepair?.collect {
                    "${it.key}=${it.value}"
                }?.join(',')]
            }
        }

        return item
    }


    private static void verifyPseudonym(File file) {
        def relPath = TestdataPseudonymValue.getProtectionDomain().getCodeSource().getLocation().getFile()
        def fileName = file.name.replaceFirst('bin', 'asn1')
        def pseudonymFile = file
        def asn1File

        if (fileName.contains('_dep')) {
            def depFileName = fileName.replaceAll('p_dep', 'dep')

            if (!depFileName.contains('div')) {
                depFileName = depFileName.replaceAll('_', '')
            }

            asn1File = new File(relPath + '../../../../pep-crypto-documentation/60-Test/data/v2/happy_flow/' + depFileName)

            def pFileName = file.name
            pseudonymFile = new File(relPath + '../../../../pep-crypto-documentation/60-Test/data/v2/pseudonyms/' + pFileName.replaceAll('dep', 'dep'))
        } else {
            if (!fileName.contains('div') && !fileName.contains('_ee')) {
                fileName = fileName.replaceAll('_', '')
            }

            if (fileName.startsWith('p_') && fileName.contains('_ee')) {
                fileName = fileName.replaceFirst('p_', 'p')
            }

            asn1File = new File(relPath + '../../../../pep-crypto-documentation/60-Test/data/v2/happy_flow/e' + fileName)
        }

        assertTrue("File not found ${asn1File}", asn1File.exists())


        def bytes = asn1File.bytes
        def asn1Object = ASN1Sequence.fromByteArray(bytes)
        def oid = asn1Object.getObjectAt(0) as String

        def mapping = ['2.16.528.1.1003.10.1.2.8': TestdataSignedEncryptedPseudonym.&signedEncryptedPseudonym,
                       '2.16.528.1.1003.10.1.2.4': TestdataDeprecatedSignedEncryptedPseudonym.&deprecatedSignedEncryptedPseudonym,
                       '2.16.528.1.1003.10.1.2.6': TestdataSignedDirectEncryptedPseudonym.&signedDirectEncryptedPseudonym
        ]

        if (mapping[oid]) {
            def item = mapping[oid](asn1File, bytes)
            def expectedPseudonym = pseudonymFile.bytes.encodeBase64().toString()
            assertEquals(expectedPseudonym, item.identifier)
        } else {
            System.err.println("Unsupported ${oid}")
        }
    }

}

package nl.logius.pepcrypto.lib.asn1.testdata.v2


import groovy.io.FileType
import nl.logius.pepcrypto.lib.asn1.testdata.TestdataSignedDirectEncryptedIdentityv2
import nl.logius.pepcrypto.lib.asn1.testdata.TestdataSignedDirectEncryptedPseudonymv2
import nl.logius.pepcrypto.lib.asn1.testdata.TestdataSignedEncryptedIdentityv2
import nl.logius.pepcrypto.lib.asn1.testdata.TestdataSignedEncryptedPseudonymv2
import org.bouncycastle.asn1.ASN1Sequence
import org.junit.Test

import static org.junit.Assert.assertEquals

class TestdataHappyFlowTest {

    @Test
    void happyFlowTestData() {
        def signedEncrypted = processTestdata()
        def actualText = renderToMarkdown(signedEncrypted)
        def expectedText = TestdataHappyFlowTest.class.getResource('TestdataHappyFlowTest.md').text.trim()

        expectedText = expectedText.replaceAll('[\r\n]', '').trim()
        actualText = actualText.replaceAll('[\r\n]', '').trim()

        assertEquals('Testdata changed, check changed and update TestDataHappyFlows.md', expectedText, actualText)
    }

    private static List<Map> processTestdata() {
        def relPath = TestdataHappyFlowTest.class.getProtectionDomain().getCodeSource().getLocation().getFile()
        def baseDir = new File(relPath + '../../../../pep-crypto-documentation/60-Test/data/v2/happy_flow/')
        def signedEncrypted = [] as List<Map>

        println "Processing directory: ${baseDir}"

        def mapping = [
                '2.16.528.1.1003.10.1.2.6.2' : TestdataSignedDirectEncryptedPseudonymv2.&signedDirectEncryptedPseudonymv2,
                '2.16.528.1.1003.10.1.2.10.2': TestdataSignedDirectEncryptedIdentityv2.&signedDirectEncryptedIdentityv2,
                '2.16.528.1.1003.10.1.2.7.2' : TestdataSignedEncryptedIdentityv2.&signedEncryptedIdentityv2,
                '2.16.528.1.1003.10.1.2.8.2' : TestdataSignedEncryptedPseudonymv2.&signedEncryptedPseudonymv2,
        ]

        baseDir.eachFileRecurse(FileType.FILES) { file ->
            if (file.canonicalPath.endsWith('.asn1')) {
                def bytes = file.bytes
                def asn1Object = ASN1Sequence.fromByteArray(bytes)
                def oid = asn1Object.getObjectAt(0) as String

                if (mapping[oid] != null) {
                    def item = mapping[oid](file, bytes) as Map

                    // Add verified data
                    signedEncrypted << item
                } else {
                    System.err.println("Unsupported oid (" + oid + ") for file " + file)
                }
            }
        }

        return signedEncrypted.collect { it }.sort {
            "${it.basicType}:${it.schemeVersion.padLeft(3, '0')}:${it.schemeKeySetVersion.padLeft(3, '0')}:${it.recipient}:${it.recipientKeySetVersion}}:${it.fileName}" as String
        }
    }

    private static String renderToMarkdown(List<Map> signedEncrypted) {
        def keys = ['fileName', 'basicType',
                    'schemeVersion', 'schemeKeySetVersion', 'recipient', 'recipientKeySetVersion', 'signingKeyVersion',
                    'envelopeOid', 'signatureOid', 'bodyOid',
                    'auditElement', 'identifier', 'r', 's',
                    'authorizedParty',
                    'issuanceDate', 'extraElements',
                    'diversifier',
        ]


        def widths = keys.collectEntries { key ->
            [(key): signedEncrypted.collect { it ->
                Math.max(key.toString().length(), it[key] ? it[key].toString().length() : 0)
            }.max()]
        }

        // Render as markdown
        def output = []
        output << '|' + keys.collect { " ${it.padRight(widths[it] + 1)}" }.join('|')
        output << '|' + keys.collect { ''.padRight(widths[it] + 2, '-') }.join('|')

        signedEncrypted.each { map ->
            output << '|' + keys.collect { k -> " ${map[k]}".padRight(widths[k] + 2, ' ') }.join('|')
        }

        // Compare to last known reference
        def actualText = output.join('\n').trim()

        println(actualText)
        actualText.trim()
    }


}

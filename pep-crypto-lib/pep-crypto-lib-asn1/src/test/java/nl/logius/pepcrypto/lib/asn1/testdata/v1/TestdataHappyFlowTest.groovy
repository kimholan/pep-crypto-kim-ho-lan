package nl.logius.pepcrypto.lib.asn1.testdata.v1

import groovy.io.FileType
import nl.logius.pepcrypto.lib.asn1.testdata.TestdataDeprecatedSignedEncryptedIdentity
import nl.logius.pepcrypto.lib.asn1.testdata.TestdataDeprecatedSignedEncryptedPseudonym
import nl.logius.pepcrypto.lib.asn1.testdata.TestdataSignedDirectEncryptedPseudonym
import nl.logius.pepcrypto.lib.asn1.testdata.TestdataSignedEncryptedIdentity
import nl.logius.pepcrypto.lib.asn1.testdata.TestdataSignedEncryptedPseudonym
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
        def baseDir = new File(relPath + '../../../../pep-crypto-documentation/60-Test/data/v1/happy_flow/')
        def signedEncrypted = [] as List<Map>

        println "Processing directory: ${baseDir}"

        def mapping = ['2.16.528.1.1003.10.1.2.6': TestdataSignedDirectEncryptedPseudonym.&signedDirectEncryptedPseudonym,
                       '2.16.528.1.1003.10.1.2.8': TestdataSignedEncryptedPseudonym.&signedEncryptedPseudonym,
                       '2.16.528.1.1003.10.1.2.4': TestdataDeprecatedSignedEncryptedPseudonym.&deprecatedSignedEncryptedPseudonym,
                       '2.16.528.1.1003.10.1.2.7': TestdataSignedEncryptedIdentity.&signedEncryptedIdentity,
                       '2.16.528.1.1003.10.1.2.3': TestdataDeprecatedSignedEncryptedIdentity.&deprecatedSignedEncryptedIdentity,
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
                    'schemeVersion', 'schemeKeySetVersion', 'signingKeyVersion',
                    'recipient', 'recipientKeySetVersion',
                    'envelopeOid', 'signatureOid', 'bodyOid',
                    'auditElement', 'identifier', 'r', 's',
                    'authorizedParty', 'diversifier'
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

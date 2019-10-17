package nl.logius.pepcrypto.lib.asn1.testdata.v1

import groovy.io.FileType
import nl.logius.pepcrypto.lib.asn1.testdata.TestdataIntermediaryPseudonym
import org.junit.Test

import static org.junit.Assert.assertEquals

class TestdataIntermediaryPseudonymsTest {

    @Test
    void happyFlowTestData() {
        def pseudonyms = processPseudonyms()
        def actualText = renderToMarkdown(pseudonyms)

        println(actualText)

        def expectedText = TestdataIntermediaryPseudonymsTest.class.getResource('TestdataIntermediaryPseudonymsTest.md').text.trim()


        expectedText = expectedText.replaceAll('[\r\n]', '').trim()
        actualText = actualText.replaceAll('[\r\n]', '').trim()


        assertEquals('Testdata changed, check changed and update TestdataIntermediaryPseudonymsTest.md', expectedText, actualText)
    }

    private static List<Map> processPseudonyms() {
        def relPath = TestdataIntermediaryPseudonymsTest.getProtectionDomain().getCodeSource().getLocation().getFile()
        def baseDir = new File(relPath + '../../../../pep-crypto-documentation/60-Test/data/v1/pseudonyms')
        def pseudonyms = [] as List<Map>

        println "Processing directory: ${baseDir}"

        baseDir.eachFileRecurse(FileType.FILES) { file ->
            if (file.name.startsWith('mip') && file.canonicalPath.endsWith('.asn1')) {
                pseudonyms << TestdataIntermediaryPseudonym.pseudonymValue(file, file.bytes)
            }
        }

        return pseudonyms.collect { it }.sort {
            "${it.schemeVersion.padLeft(3, '0')}:${it.schemeKeySetVersion.padLeft(3, '0')}:${it.source}:${it.sourceKeySetVersion}:${it.target}:${it.targetKeySetVersion}:${it.type}:${it.fileName}" as String
        }
    }


    private static String renderToMarkdown(List<Map> pseudonyms) {
        def keys = ['fileName',
                    'oid',
                    'schemeVersion', 'schemeKeySetVersion',
                    'source', 'sourceKeySetVersion', 'target', 'targetKeySetVersion',
                    'type',
                    'pseudonymValue',
                    'diversifier'
        ]

        def widths = keys.collectEntries { key ->
            [(key): pseudonyms.collect { it ->
                Math.max(key.toString().length(), it[key] ? it[key].toString().length() : 0)
            }.max()]
        }

        // Render as markdown
        def output = []
        output << '|' + keys.collect { " ${it.padRight(widths[it] + 1)}" }.join('|')
        output << '|' + keys.collect { ''.padRight(widths[it] + 2, '-') }.join('|')

        pseudonyms.each { map ->
            output << '|' + keys.collect { k -> " ${map[k]}".padRight(widths[k] + 2, ' ') }.join('|')
        }

        // Compare to last known reference
        def actualText = output.join('\n').trim()
        actualText
    }

}

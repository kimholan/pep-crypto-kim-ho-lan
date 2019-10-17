package nl.logius.pepcrypto.lib.asn1.testdata.v2

import groovy.io.FileType
import nl.logius.pepcrypto.lib.crypto.PepCrypto
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.asn1.sec.ECPrivateKey
import org.bouncycastle.asn1.x509.AlgorithmIdentifier
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers
import org.bouncycastle.openssl.PEMParser
import org.junit.Assert
import org.junit.Test

import static org.bouncycastle.asn1.ASN1Sequence.getInstance

class TestdataKeysTest {

    @Test
    void happyFlowKeys() {
        def relPath = getClass().getProtectionDomain().getCodeSource().getLocation().getFile()
        def baseDir = new File(relPath + '../../../../pep-crypto-documentation/60-Test/data/v2/keys')

        def pemKeys = []

        baseDir.eachFileRecurse(FileType.FILES) { file ->
            if (file.canonicalPath.endsWith('.pem')) {
                def stringReader = new StringReader(file.text)
                def reader = new PEMParser(stringReader)
                def pemObject = reader.readPemObject()
                def headers = pemObject.headers

                def itemHeaders = [:]

                headers.each { it ->
                    def headerName = it.name
                    def headerValue = it.value

                    if (!itemHeaders.containsKey(headerName)) {
                        itemHeaders[headerName] = headerValue
                    }
                }


                def pemObjecContent = getInstance(pemObject.content)
                def ecPrivateKey = ECPrivateKey.getInstance(pemObjecContent)
                def algorithmIdentifier = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, ecPrivateKey.getParameters())
                def privateKeyInfo = new PrivateKeyInfo(algorithmIdentifier, ecPrivateKey)

                def privateKeyValue = new BigInteger(1, privateKeyInfo.parsePrivateKey().getObjectAt(1).octets)


                def item = [
                        fileName       : file.name,
                        pemType        : pemObject.type,
                        itemHeaders    : itemHeaders,
                        privateKeyValue: privateKeyValue,
                        headers        : headers,
                ]


                if (ecPrivateKey.publicKey) {
                    def publicKeyInfo = new SubjectPublicKeyInfo(algorithmIdentifier, ecPrivateKey.publicKey.getBytes())
                    def publicKeyValue = PepCrypto.decodeEcPoint(publicKeyInfo.publicKeyData.bytes)

                    item << [publicKeyValue: publicKeyValue.getEncoded(false).encodeBase64() as String]
                }

                pemKeys << item
            }
        }

        def keys = ['fileName', 'pemType', 'privateKeyValue', 'publicKeyValue']
        def widths = keys.collectEntries { key ->
            [(key): pemKeys.collect { it ->
                Math.max(key.toString().length(), it[key] ? it[key].toString().length() : 0)
            }.max()]
        }

        def headerKeys = ['Type', 'SchemeVersion', 'SchemeKeySetVersion', 'SchemeKeyVersion', 'Recipient', 'RecipientKeySetVersion']
        def headerWidths = headerKeys.collectEntries { key ->
            [(key): pemKeys.collect { it ->
                Math.max(key.toString().length(), it.itemHeaders[key] ? it.itemHeaders[key].toString().length() : 0)
            }.max()]
        }


        def output = []
        output << '|' + [
                *['fileName', 'pemType'].collect { " ${it.padRight(widths[it] + 1)}" },
                *headerKeys.collect { " ${it.padRight(headerWidths[it] + 1)}" },
                *['privateKeyValue', 'publicKeyValue'].collect { " ${it.padRight(widths[it] + 1)}" },
                'PEM Headers'
        ].join('|')


        output << '|' + [
                *['fileName', 'pemType'].collect { "${''.padRight(widths[it] + 2, '-')}" },
                *headerKeys.collect { "${''.padRight(headerWidths[it] + 2, '-')}" },
                *['privateKeyValue', 'publicKeyValue'].collect { "${''.padRight(widths[it] + 2, '-')}" },
                ''.padRight('PEM Headers'.length() + 2, '-')
        ].join('|')


        pemKeys.sort {
            "${it.itemHeaders.Type}:${it.itemHeaders.SchemeVersion}:${it.itemHeaders.SchemeKeySetVersion}:${it.itemHeaders.SchemeKeyVersion}:${it.itemHeaders.Recipient}:${it.itemHeaders.RecipientKeySetVersion}:${it.fileName}:${it.headers}"
        }.each { map ->
            output << '|' + [
                    *['fileName', 'pemType'].collect { k -> " ${map[k]}".padRight(widths[k] + 2, ' ') },
                    *headerKeys.collect { k -> " ${(map.itemHeaders[k] ?: '').padRight(headerWidths[k] + 1, ' ')}" },
                    *['privateKeyValue', 'publicKeyValue'].collect { k -> " ${map[k]}".padRight(widths[k] + 2, ' ') },
                    map.headers.collect { [(it.name): it.value] }
            ].join('|')
        }


        // Compare to last known reference
        def expectedText = TestdataHappyFlowTest.class.getResource('TestdataKeysTest.md').text.trim()
        def actualText = output.join('\n').trim()

        println(actualText)

        expectedText = expectedText.replaceAll('[\r\n]', '').trim()
        actualText = actualText.replaceAll('[\r\n]', '').trim()

        Assert.assertEquals('Keys changed, check changed and update TestdataKeysTest.md', expectedText, actualText)
    }


}

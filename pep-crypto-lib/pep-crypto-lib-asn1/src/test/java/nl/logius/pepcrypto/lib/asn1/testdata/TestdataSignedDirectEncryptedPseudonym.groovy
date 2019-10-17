package nl.logius.pepcrypto.lib.asn1.testdata

import nl.logius.pepcrypto.lib.TestPepEcPoint
import nl.logius.pepcrypto.lib.TestResources
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope
import nl.logius.pepcrypto.lib.crypto.PepEcdsaVerification
import nl.logius.pepcrypto.lib.crypto.algorithm.PepDirectEncryptedPseudonymDecryption
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedPseudonymDecryption
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyReader

import static nl.logius.pepcrypto.lib.TestPemObjects.drkiFromResource
import static nl.logius.pepcrypto.lib.TestPemObjects.epClosingFromResource
import static nl.logius.pepcrypto.lib.TestPemObjects.epDecryptionFromResource
import static nl.logius.pepcrypto.lib.TestPemObjects.readSchemePublicKeyValueFromResource
import static nl.logius.pepcrypto.lib.TestVerificationKeys.newPublicVerificationKey
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.DIVERSIFIER
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class TestdataSignedDirectEncryptedPseudonym {


    static Map signedDirectEncryptedPseudonym(File file, byte[] bytes) {
        def asn1 = Asn1SignedDirectEncryptedPseudonymEnvelope.fromByteArray(bytes)

        def div = ''
        if (file.name.contains('div')) {
            div = file.name.replaceAll('.*_(div[0-9]*)\\..*', '$1') + '_'
        }

        def recipientKeyId = asn1.asAsn1RecipientKeyId()
        def schemeVersion = recipientKeyId.schemeVersion
        def schemeKeySetVersion = recipientKeyId.schemeKeySetVersion
        def recipient = recipientKeyId.recipient
        def recipientKeySetVersion = recipientKeyId.recipientKeySetVersion
        def signingKeyVersion = asn1.asn1SigningKeyVersion()

        def uResource = "/v1/keys/u_sksv_${schemeKeySetVersion}_kv_${signingKeyVersion}.pem"
        def drkiResource = "/v1/keys/drki_${div}sksv_${schemeKeySetVersion}_oin_${recipient}_kv_${recipientKeySetVersion}.pem"
        def pddResource = "/v1/keys/pdd_sksv_${schemeKeySetVersion}_oin_${recipient}_kv_${recipientKeySetVersion}.pem"
        def pcdResource = "/v1/keys/pcd_sksv_${schemeKeySetVersion}_oin_${recipient}_ckv_${recipientKeySetVersion}.pem"

        if (div) {
            def pemString = TestResources.resourceToString(drkiResource)
            def reader = new PemEcPrivateKeyReader()
            def key = reader.parsePemEcPrivateKey(pemString)

            def actualDiversifier = key.getSpecifiedHeader(DIVERSIFIER)
            def expectedDiversifier = asn1.asn1PseudonymDiversifier().asn1DiversifierString()

            assertEquals(expectedDiversifier, actualDiversifier)
        }


        def pseudonymValue = signedDirectEncryptedPseudonym(asn1,
                uResource,
                drkiResource,
                pddResource,
                pcdResource
        )


        return TestdataAuditAndSignature.assertExpectedValues(file, [
                basicType             : 'SDEP',
                fileName              : file.name,
                identifier            : pseudonymValue,
                envelopeOid           : asn1.asn1Oid(),
                bodyOid               : asn1.asn1BodyOid(),
                signatureOid          : asn1.asn1SignatureOid(),
                auditElement          : asn1.asn1AuditElement().encodeBase64() as String,
                schemeVersion         : schemeVersion as String,
                schemeKeySetVersion   : schemeKeySetVersion as String,
                recipient             : recipient as String,
                recipientKeySetVersion: recipientKeySetVersion as String,
                signingKeyVersion     : asn1.asn1SigningKeyVersion() as String,
                r                     : asn1.asn1Signature().getR().toByteArray().encodeBase64() as String,
                s                     : asn1.asn1Signature().getS().toByteArray().encodeBase64() as String,
                authorizedParty       : asn1.asn1AuthorizedParty(),
                diversifier           : asn1.asn1PseudonymDiversifier()?.diversifierkeyvaluepair?.collect {
                    "${it.key}=${it.value}"
                }?.join(',')
        ])
    }


    static String signedDirectEncryptedPseudonym(Asn1SignedDirectEncryptedPseudonymEnvelope asn1, String schemeKeyResource, String drkiResource, String pddResource, String pcdResource) {
        def schemePublicKey = readSchemePublicKeyValueFromResource(schemeKeyResource)
        def drkiKey = drkiFromResource(drkiResource)
        def pddKey = epDecryptionFromResource(pddResource)
        def pcdKey = epClosingFromResource(pcdResource)
        def verificationKey = newPublicVerificationKey(schemePublicKey)

        def signedData = asn1.asn1SignedData()
        def signature = asn1.asn1Signature()

        assertTrue("Signature verification failed", PepEcdsaVerification.verify(signedData, verificationKey, signature))

        // Decode/reshuffle to pseudonym

        def decryptToEncryptedPseudonym = PepDirectEncryptedPseudonymDecryption.asFunction(drkiKey)
        def decryptEncryptedPseudonym = PepEncryptedPseudonymDecryption.asFunction(pcdKey, pddKey)
        def depDecryption = decryptToEncryptedPseudonym.andThen(decryptEncryptedPseudonym)

        def reshuffeldDecryptedInput = depDecryption.apply(asn1.asn1EcPointTriplet())

        return TestPepEcPoint.toBase64(reshuffeldDecryptedInput)
    }
}

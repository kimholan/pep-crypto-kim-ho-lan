package nl.logius.pepcrypto.lib.asn1.testdata

import nl.logius.pepcrypto.lib.TestPepEcPoint
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope
import nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym.Asn1SignedEncryptedPseudonymEnvelope
import nl.logius.pepcrypto.lib.crypto.PepEcsdsaVerification
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedPseudonymDecryption

import static nl.logius.pepcrypto.lib.TestPemObjects.epClosingFromResource
import static nl.logius.pepcrypto.lib.TestPemObjects.epDecryptionFromResource
import static nl.logius.pepcrypto.lib.TestPemObjects.readSchemePublicKeyValueFromResource
import static nl.logius.pepcrypto.lib.TestVerificationKeys.newSchnorrVerificationKey
import static org.junit.Assert.assertTrue

class TestdataSignedEncryptedPseudonym {

    static Map signedEncryptedPseudonym(File file, byte[] bytes) {
        def asn1 = Asn1SignedEncryptedPseudonymEnvelope.fromByteArray(bytes)

        def recipientKeyId = asn1.asAsn1RecipientKeyId()
        def schemeVersion = recipientKeyId.schemeVersion
        def schemeKeySetVersion = recipientKeyId.schemeKeySetVersion
        def recipient = recipientKeyId.recipient
        def recipientKeySetVersion = recipientKeyId.recipientKeySetVersion

        def pseudonymValue = signedEncryptedPseudonym(asn1,
                "/v1/keys/ppp_sksv_${schemeKeySetVersion}_kv_${schemeKeySetVersion}.pem",
                "/v1/keys/pdd_sksv_${schemeKeySetVersion}_oin_${recipient}_kv_${recipientKeySetVersion}.pem",
                "/v1/keys/pcd_sksv_${schemeKeySetVersion}_oin_${recipient}_ckv_${recipientKeySetVersion}.pem"
        )

        return TestdataAuditAndSignature.assertExpectedValues(file, [
                basicType             : 'SEP',
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
                r                     : asn1.asn1Signature().getR().toByteArray().encodeBase64() as String,
                s                     : asn1.asn1Signature().getS().toByteArray().encodeBase64() as String,
                diversifier           : asn1.asn1PseudonymDiversifier()?.diversifierkeyvaluepair?.collect {
                    "${it.key}=${it.value}"
                }?.join(',')
        ])
    }


    static String signedEncryptedPseudonym(Asn1PseudonymEnvelope asn1, String schemeKeyResource, String pddResource, String pcdResource) {
        def schemePublicKey = readSchemePublicKeyValueFromResource(schemeKeyResource)
        def pddKey = epDecryptionFromResource(pddResource)
        def pcdKey = epClosingFromResource(pcdResource)
        def verificationKey = newSchnorrVerificationKey(schemePublicKey, pddKey.getValue())

        def signedData = asn1.asn1SignedData()
        def signature = asn1.asn1Signature()

        assertTrue("Signature verification failed", PepEcsdsaVerification.verify(signedData, verificationKey, signature))

        // Decode/reshuffle to pseudonym
        def reshuffeldDecryptedInput = PepEncryptedPseudonymDecryption.epDecryption(asn1.asn1EcPointTriplet(), pcdKey, pddKey)

        return TestPepEcPoint.toBase64(reshuffeldDecryptedInput)
    }
}

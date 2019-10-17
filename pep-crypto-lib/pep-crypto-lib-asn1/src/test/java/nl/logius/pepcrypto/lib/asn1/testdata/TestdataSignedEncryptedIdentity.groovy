package nl.logius.pepcrypto.lib.asn1.testdata


import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope
import nl.logius.pepcrypto.lib.asn1.signedencryptedidentity.Asn1SignedEncryptedIdentityEnvelope
import nl.logius.pepcrypto.lib.crypto.PepEcsdsaVerification
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoded
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoder

import static nl.logius.pepcrypto.lib.TestPemObjects.readPrivateKeyValueFromResource
import static nl.logius.pepcrypto.lib.TestPemObjects.readSchemePublicKeyValueFromResource
import static nl.logius.pepcrypto.lib.TestVerificationKeys.newSchnorrVerificationKey
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class TestdataSignedEncryptedIdentity {

    static Map signedEncryptedIdentity(File file, byte[] bytes) {
        def asn1 = Asn1SignedEncryptedIdentityEnvelope.fromByteArray(bytes)

        def recipientKeyId = asn1.asAsn1RecipientKeyId()
        def schemeVersion = recipientKeyId.schemeVersion
        def schemeKeySetVersion = recipientKeyId.schemeKeySetVersion
        def recipient = recipientKeyId.recipient
        def recipientKeySetVersion = recipientKeyId.recipientKeySetVersion

        def identifier = signedEncryptedIdentity(asn1,
                "/v1/keys/ipp_sksv_${schemeKeySetVersion}_kv_${schemeKeySetVersion}.pem",
                "/v1/keys/idd_sksv_${schemeKeySetVersion}_oin_${recipient}_kv_${recipientKeySetVersion}.pem"
        )

        return TestdataAuditAndSignature.assertExpectedValues(file, [
                basicType             : 'SEI',
                fileName              : file.name,
                identifier            : identifier,
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
        ])
    }


    static String signedEncryptedIdentity(Asn1IdentityEnvelope asn1, String schemeKeyResource, String iddResource) {
        def schemePublicKey = readSchemePublicKeyValueFromResource(schemeKeyResource)
        def iddKey = readPrivateKeyValueFromResource(iddResource)
        def verificationKey = newSchnorrVerificationKey(schemePublicKey, iddKey)

        def signedData = asn1.asn1SignedData()
        def signature = asn1.asn1Signature()

        assertTrue("Signature verification failed", PepEcsdsaVerification.verify(signedData, verificationKey, signature))

        // Decode/reshuffle to pseudonym
        def decryptedInput = identityDecryption(asn1.asn1EcPointTriplet(), iddKey)
        def decodedEcPoint = PepIdentityOaepDecoder.oaepDecode(decryptedInput)
        def decodedIdentity = new PepIdentityOaepDecoded(decodedEcPoint)

        assertEquals("01", decodedIdentity.getVersion())
        assertEquals("B", decodedIdentity.getType())

        return decodedIdentity.identifier
    }


}

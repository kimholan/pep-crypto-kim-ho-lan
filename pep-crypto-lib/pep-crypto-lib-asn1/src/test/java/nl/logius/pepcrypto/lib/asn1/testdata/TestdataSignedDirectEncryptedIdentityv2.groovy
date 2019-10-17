package nl.logius.pepcrypto.lib.asn1.testdata

import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope
import nl.logius.pepcrypto.lib.crypto.PepEcdsaVerification
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoded
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoder

import static nl.logius.pepcrypto.lib.TestPemObjects.eiDecryptionFromResource
import static nl.logius.pepcrypto.lib.TestPemObjects.readSchemePublicKeyValueFromResource
import static nl.logius.pepcrypto.lib.TestVerificationKeys.newPublicVerificationKey
import static nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedIdentityDecryption.eiDecryption
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

class TestdataSignedDirectEncryptedIdentityv2 {

    static Map signedDirectEncryptedIdentityv2(File file, byte[] bytes) {
        try {
            def asn1 = Asn1SignedDirectEncryptedIdentityv2Envelope.fromByteArray(bytes)

            def recipientKeyId = asn1.asAsn1RecipientKeyId()
            def schemeVersion = recipientKeyId.schemeVersion
            def schemeKeySetVersion = recipientKeyId.schemeKeySetVersion
            def recipient = recipientKeyId.recipient
            def recipientKeySetVersion = recipientKeyId.recipientKeySetVersion
            def signingKeyVersion = asn1.asn1SigningKeyVersion()

            def identifier = signedDirectEncryptedIdentity(asn1,
                    "/v2/keys/u_sksv_${schemeKeySetVersion}_kv_${signingKeyVersion}.pem",
                    "/v2/keys/idd_sksv_${schemeKeySetVersion}_oin_${recipient}_kv_${recipientKeySetVersion}.pem"
            )

            return TestdataAuditAndSignature.assertExpectedValues(file, [
                    basicType             : 'SDEI-V2',
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
                    signingKeyVersion     : asn1.asn1SigningKeyVersion() as String,
                    issuanceDate          : asn1.asn1SignedBody().getIssuanceDate(),
                    extraElements         : asn1.asn1SignedBody().getExtraElements()?.asn1ExtraelementskeyvaluepairAsString(),
                    r                     : asn1.asn1Signature().getR().toByteArray().encodeBase64() as String,
                    s                     : asn1.asn1Signature().getS().toByteArray().encodeBase64() as String,
                    authorizedParty       : asn1.asn1AuthorizedParty(),
            ])
        } catch (e) {
            return [
                    basicType             : e,
                    fileName              : file.name,
                    identifier            : 'ERROR',
                    envelopeOid           : 'ERROR',
                    bodyOid               : 'ERROR',
                    signatureOid          : 'ERROR',
                    auditElement          : 'ERROR',
                    schemeVersion         : 'ERROR',
                    schemeKeySetVersion   : 'ERROR',
                    recipient             : 'ERROR',
                    recipientKeySetVersion: 'ERROR',
                    signingKeyVersion     : 'ERROR',
                    issuanceDate          : 'ERROR',
                    extraElements         : 'ERROR',
                    r                     : 'ERROR',
                    s                     : 'ERROR',
                    authorizedParty       : 'ERROR',
            ]
        }
    }


    static String signedDirectEncryptedIdentity(Asn1SignedDirectEncryptedIdentityv2Envelope asn1, String schemeKeyResource, String iddResource) {
        def schemePublicKey = readSchemePublicKeyValueFromResource(schemeKeyResource)
        def iddKey = eiDecryptionFromResource(iddResource)
        def verificationKey = newPublicVerificationKey(schemePublicKey)

        def signedData = asn1.asn1SignedData()
        def signature = asn1.asn1Signature()

        assertTrue("Signature verification failed", PepEcdsaVerification.verify(signedData, verificationKey, signature))

        // Decode/reshuffle to pseudonym
        def decryptedInput = eiDecryption(asn1.asn1EcPointTriplet(), iddKey)
        def decodedEcPoint = PepIdentityOaepDecoder.oaepDecode(decryptedInput)
        def decodedIdentity = new PepIdentityOaepDecoded(decodedEcPoint)

        assertEquals("01", decodedIdentity.getVersion())
        assertEquals("B", decodedIdentity.getType())

        return decodedIdentity.identifier
    }


}

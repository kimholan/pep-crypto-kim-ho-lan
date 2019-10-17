package nl.logius.pepcrypto.lib.asn1.testdata


import nl.logius.pepcrypto.lib.asn1.signedencryptedidentityv2.Asn1SignedEncryptedIdentityv2Envelope

class TestdataSignedEncryptedIdentityv2 {

    static Map signedEncryptedIdentityv2(File file, byte[] bytes) {
        try {
            def asn1 = Asn1SignedEncryptedIdentityv2Envelope.fromByteArray(bytes)

            def recipientKeyId = asn1.asAsn1RecipientKeyId()
            def schemeVersion = recipientKeyId.schemeVersion
            def schemeKeySetVersion = recipientKeyId.schemeKeySetVersion
            def recipient = recipientKeyId.recipient
            def recipientKeySetVersion = recipientKeyId.recipientKeySetVersion

            return TestdataAuditAndSignature.assertExpectedValues(file, [
                    basicType             : 'SEI-v2',
                    fileName              : file.name,
                    identifier            : 'TODO',
                    envelopeOid           : asn1.asn1Oid(),
                    bodyOid               : asn1.asn1BodyOid(),
                    signatureOid          : asn1.asn1SignatureOid(),
                    auditElement          : asn1.asn1AuditElement().encodeBase64() as String,
                    schemeVersion         : schemeVersion as String,
                    schemeKeySetVersion   : schemeKeySetVersion as String,
                    recipient             : recipient as String,
                    recipientKeySetVersion: recipientKeySetVersion as String,
                    issuanceDate          : asn1.asn1SignedBody().getIssuanceDate(),
                    extraElements         : asn1.asn1SignedBody().getExtraElements()?.asn1ExtraelementskeyvaluepairAsString(),
                    r                     : asn1.asn1Signature().getR().toByteArray().encodeBase64() as String,
                    s                     : asn1.asn1Signature().getS().toByteArray().encodeBase64() as String,
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
                    issuanceDate          : 'ERROR',
                    extraElements         : 'ERROR',
                    r                     : 'ERROR',
                    s                     : 'ERROR',
            ]
        }
    }


}

package nl.logius.pepcrypto.lib.asn1.testdata

import static org.junit.Assert.assertEquals

class TestdataAuditAndSignature {

    static Map assertExpectedValues(File file, Map item) {
        // Verify auditElement
        def expectedAuditElement = new File(file.canonicalPath.replaceFirst('.asn1', '.audit')).text.trim()
        assertEquals("${file}: Audit element must match", expectedAuditElement, item.auditElement)


        // Verify signature values
        def signatureLines = new File(file.canonicalPath.replaceFirst('.asn1', '.signature')).readLines()
        def expectedSignatureOid = signatureLines[1]
        assertEquals("${file}: Signature OID must match", expectedSignatureOid, item.signatureOid)

        def expectedR = signatureLines[3]
        assertEquals("${file}: Signature R must match", expectedR, item.r)

        def expectedS = signatureLines[5]
        assertEquals("${file}: Signature S must match", expectedS, item.s)

        return item
    }

}

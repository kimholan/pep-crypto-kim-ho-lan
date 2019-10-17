package nl.logius.pepcrypto.api;

import nl.logius.pepcrypto.lib.TestResources;
import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.junit.Test;

import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.API_MODULE;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_1;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_7;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ApiAsn1SequenceDecodableTest
        implements ApiAsn1SequenceDecodable {

    private String oid;

    private byte[] rawInput;

    @Test
    public void decodeRawInput() {
        rawInput = TestResources.resourceToByteArray("/legacy/ei02.asn1");
        assertNull(getOid());

        ApiAsn1SequenceDecodable.decodeRawInput(this);

        assertEquals("2.16.528.1.1003.10.1.2.7", getOid());
    }

    @Test
    public void decodeRawInputNull() {
        assertNull(getOid());

        try {
            ApiAsn1SequenceDecodable.decodeRawInput(this);
            fail();
        } catch (PepRuntimeException cause) {
            assertTrue(cause.getCause() instanceof NullPointerException);
        }
    }

    @Test
    public void requireMatchingOidType() {
        rawInput = TestResources.resourceToByteArray("/legacy/ei02.asn1");
        assertNull(getOid());

        ApiAsn1SequenceDecodable.decodeRawInput(this);
        var oidAnnotationLiteral = requireMatchingOidType(API_MODULE, OID_2_16_528_1_1003_10_1_2_7);

        assertSame(OID_2_16_528_1_1003_10_1_2_7, oidAnnotationLiteral.value());
    }

    @Test
    public void requireMatchingOidTypeNone() {
        rawInput = TestResources.resourceToByteArray("/legacy/ei02.asn1");
        assertNull(getOid());

        ApiAsn1SequenceDecodable.decodeRawInput(this);

        try {
            requireMatchingOidType(API_MODULE);
            fail();
        } catch (PepRuntimeException cause) {
            // expected
        }
    }

    @Test
    public void requireMatchingOidTypeNull() {
        rawInput = TestResources.resourceToByteArray("/legacy/ei02.asn1");
        assertNull(getOid());

        ApiAsn1SequenceDecodable.decodeRawInput(this);

        try {
            requireMatchingOidType(API_MODULE, null, null);
            fail();
        } catch (PepRuntimeException cause) {
            // expected
        }
    }

    @Test
    public void requireMatchingOidTypeNoMatches() {
        rawInput = TestResources.resourceToByteArray("/legacy/ei02.asn1");
        assertNull(getOid());

        ApiAsn1SequenceDecodable.decodeRawInput(this);

        try {
            requireMatchingOidType(API_MODULE, OID_2_16_528_1_1003_10_1_2_1);
            fail();
        } catch (PepRuntimeException cause) {
            // expected
        }
    }

    @Override
    public byte[] getRawInput() {
        return rawInput;
    }

    @Override
    public String getOid() {
        return oid;
    }

    @Override
    public void setOid(String oid) {
        this.oid = oid;
    }

}

package nl.logius.pepcrypto.lib.asn1.testdata;

import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.util.List;

import static java.math.BigInteger.ONE;
import static nl.logius.pepcrypto.lib.TestResources.resourceToBase64;
import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static nl.logius.pepcrypto.lib.TestResources.resourceToString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

@RunWith(Parameterized.class)
public class TestdataPseudonymTest {

    @Parameter(0)
    public String fileName;

    @Parameters
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {"p_01.asn1"},
                {"p_02.asn1"},
                {"p_03.asn1"},
                {"p_04.asn1"},
                {"p_05.asn1"},
                {"p_06.asn1"},
                {"p_07.asn1"},
                {"p_08.asn1"},
                {"p_09.asn1"},
                {"p_09.asn1"},
//                {"p_10.asn1"}, // Not available
//                {"p_11.asn1"}, // Not available
                {"p_12.asn1"},
                {"p_13.asn1"},
                {"p_14.asn1"},
                {"p_15.asn1"},
        });
    }

    @Test
    public void sanityCheck() throws IOException {
        var bytes = resourceToByteArray("/v1/pseudonyms/" + fileName);

        assertNotNull(bytes);
        assertNotEquals(0, bytes.length);

        // Dump using BC for failing cases
        var inputAsn1 = ASN1Primitive.fromByteArray(bytes);
        var inputDump = ASN1Dump.dumpAsString(inputAsn1);

        var mapped = Asn1DecryptedPseudonym.fromByteArray(bytes);
        var encoded = mapped.encodeByteArray();

        // Should not be the same array, preferably encoding is actualy performed
        assertNotSame("Encoded array should not be the same array as the input", bytes, encoded);

        // The encoding should match the input assuming the input is DER-encoded
        var encodedAsn1 = ASN1Primitive.fromByteArray(encoded);
        var encodedDump = ASN1Dump.dumpAsString(encodedAsn1);

        assertEquals("Content should match structurally", inputDump, encodedDump);
        assertArrayEquals("Content should match bytewise", bytes, encoded);
        assertEquals(ONE, mapped.getSchemeVersion());

        // Match with the other provided representations of the pseudonym values
        var mappedPseudonymValue = mapped.getPseudonymValue();

        var binBase64 = resourceToBase64("/v1/pseudonyms/" + fileName.replaceFirst("asn1$", "bin"));
        assertEquals(mappedPseudonymValue, binBase64);

        var string = resourceToString("/v1/pseudonyms/" + fileName.replaceFirst("asn1$", "txt"));
        assertEquals(mappedPseudonymValue, string);
    }

}

package nl.logius.pepcrypto.lib.crypto;

import nl.logius.pepcrypto.lib.TestPemObjects;
import nl.logius.pepcrypto.lib.TestSignedEncryptedIdentity;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedIdentityDecryption;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class PepIdentityOaepDecoderTest {

    /**
     * Decodes the decrypted identify from a SEI without any verification.
     */
    @Test
    public void decodeDecryptedEcPoint() {
        var ecPointTriplet = TestSignedEncryptedIdentity.fromResource("/v1/happy_flow/ei02.asn1");
        var privateKey = TestPemObjects.eiDecryptionFromResource("/v1/keys/idd_sksv_1_oin_99990020000000000002_kv_20191103.pem");
        var decryptedInput = PepEncryptedIdentityDecryption.eiDecryption(ecPointTriplet, privateKey);
        var result = new PepIdentityOaepDecoded(decryptedInput);

        assertEquals("799890133", result.getIdentifier());
        assertEquals("01", result.getVersion());
        assertEquals("B", result.getType());
        assertEquals(9, result.getLength());

        var expected = PepIdentityOaepDecoder.oaepDecode(decryptedInput);
        assertNotSame(expected, result.getBytes());
        assertArrayEquals(expected, result.getBytes());
    }

}

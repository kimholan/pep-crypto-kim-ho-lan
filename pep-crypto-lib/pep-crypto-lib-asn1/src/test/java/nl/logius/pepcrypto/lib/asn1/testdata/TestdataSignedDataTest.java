package nl.logius.pepcrypto.lib.asn1.testdata;

import nl.logius.pepcrypto.lib.TestPepEcPoint;
import nl.logius.pepcrypto.lib.asn1.Asn1Envelope;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.asn1.signedencryptedidentity.Asn1DeprecatedSignedEncryptedIdentityEnvelope;
import nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym.Asn1DeprecatedSignedEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerification;
import nl.logius.pepcrypto.lib.crypto.PepEcdsaVerification;
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoded;
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoder;
import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import static java.math.BigInteger.ONE;
import static nl.logius.pepcrypto.lib.TestPemObjects.eiDecryptionFromResource;
import static nl.logius.pepcrypto.lib.TestPemObjects.epClosingFromResource;
import static nl.logius.pepcrypto.lib.TestPemObjects.epDecryptionFromResource;
import static nl.logius.pepcrypto.lib.TestPemObjects.readSchemePublicKeyValueFromResource;
import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static nl.logius.pepcrypto.lib.TestVerificationKeys.newPublicVerificationKey;
import static nl.logius.pepcrypto.lib.TestVerificationKeys.newSchnorrVerificationKey;
import static nl.logius.pepcrypto.lib.asn1.testdata.TestdataSignedDataTest.Asn1Type.DEPPRECATED_SEI;
import static nl.logius.pepcrypto.lib.asn1.testdata.TestdataSignedDataTest.Asn1Type.DEPPRECATED_SEP;
import static nl.logius.pepcrypto.lib.asn1.testdata.TestdataSignedDataTest.Asn1Type.SDEP;
import static nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedIdentityDecryption.eiDecryption;
import static nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedPseudonymDecryption.epDecryption;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class TestdataSignedDataTest {

    @Parameter(0)
    public String fileName;

    @Parameter(1)
    public Asn1Type typeName;

    @Parameters
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {"dep12.asn1", SDEP},
                {"dep12_div1.asn1", SDEP},
                {"dep12_div2.asn1", SDEP},
                {"dep12_div3.asn1", SDEP},
                {"dep13.asn1", SDEP},
                {"dep13_div1.asn1", SDEP},
                {"dep13_div2.asn1", SDEP},
                {"dep13_div3.asn1", SDEP},
                {"dep14.asn1", SDEP},
                {"dep14_div1.asn1", SDEP},
                {"dep14_div2.asn1", SDEP},
                {"dep14_div3.asn1", SDEP},
                {"dep15.asn1", SDEP},
                {"dep15_div1.asn1", SDEP},
                {"dep15_div2.asn1", SDEP},
                {"dep15_div3.asn1", SDEP},
                {"ei01.asn1", DEPPRECATED_SEI},
                {"ei02.asn1", DEPPRECATED_SEI},
                {"ei04.asn1", DEPPRECATED_SEI},
                {"ei06.asn1", DEPPRECATED_SEI},
                {"ei07.asn1", DEPPRECATED_SEI},
                {"ei09.asn1", DEPPRECATED_SEI},
                {"ei12.asn1", DEPPRECATED_SEI},
                {"ei13.asn1", DEPPRECATED_SEI},
                {"ei14.asn1", DEPPRECATED_SEI},
                {"ei15.asn1", DEPPRECATED_SEI},
                {"ep01.asn1", DEPPRECATED_SEP},
                {"ep01_div1.asn1", DEPPRECATED_SEP},
                {"ep01_div2.asn1", DEPPRECATED_SEP},
                {"ep01_div3.asn1", DEPPRECATED_SEP},
                {"ep02.asn1", DEPPRECATED_SEP},
                {"ep02_div1.asn1", DEPPRECATED_SEP},
                {"ep02_div2.asn1", DEPPRECATED_SEP},
                {"ep02_div3.asn1", DEPPRECATED_SEP},
                {"ep03.asn1", DEPPRECATED_SEP},
                {"ep03_div1.asn1", DEPPRECATED_SEP},
                {"ep03_div2.asn1", DEPPRECATED_SEP},
                {"ep03_div3.asn1", DEPPRECATED_SEP},
                {"ep04.asn1", DEPPRECATED_SEP},
                {"ep04_div1.asn1", DEPPRECATED_SEP},
                {"ep04_div2.asn1", DEPPRECATED_SEP},
                {"ep04_div3.asn1", DEPPRECATED_SEP},
                {"ep05.asn1", DEPPRECATED_SEP},
                {"ep05_div1.asn1", DEPPRECATED_SEP},
                {"ep05_div2.asn1", DEPPRECATED_SEP},
                {"ep05_div3.asn1", DEPPRECATED_SEP},
                {"ep06.asn1", DEPPRECATED_SEP},
                {"ep06_div1.asn1", DEPPRECATED_SEP},
                {"ep06_div2.asn1", DEPPRECATED_SEP},
                {"ep06_div3.asn1", DEPPRECATED_SEP},
                {"ep07.asn1", DEPPRECATED_SEP},
                {"ep07_div1.asn1", DEPPRECATED_SEP},
                {"ep07_div2.asn1", DEPPRECATED_SEP},
                {"ep07_div3.asn1", DEPPRECATED_SEP},
                {"ep08.asn1", DEPPRECATED_SEP},
                {"ep08_div1.asn1", DEPPRECATED_SEP},
                {"ep08_div2.asn1", DEPPRECATED_SEP},
                {"ep08_div3.asn1", DEPPRECATED_SEP},
                {"ep09.asn1", DEPPRECATED_SEP},
                {"ep09_div1.asn1", DEPPRECATED_SEP},
                {"ep09_div2.asn1", DEPPRECATED_SEP},
                {"ep09_div3.asn1", DEPPRECATED_SEP},
                {"ep12.asn1", DEPPRECATED_SEP},
                {"ep12_div1.asn1", DEPPRECATED_SEP},
                {"ep12_div2.asn1", DEPPRECATED_SEP},
                {"ep12_div3.asn1", DEPPRECATED_SEP},
                {"ep13.asn1", DEPPRECATED_SEP},
                {"ep13_div1.asn1", DEPPRECATED_SEP},
                {"ep13_div2.asn1", DEPPRECATED_SEP},
                {"ep13_div3.asn1", DEPPRECATED_SEP},
                {"ep14.asn1", DEPPRECATED_SEP},
                {"ep14_div1.asn1", DEPPRECATED_SEP},
                {"ep14_div2.asn1", DEPPRECATED_SEP},
                {"ep14_div3.asn1", DEPPRECATED_SEP},
                {"ep15.asn1", DEPPRECATED_SEP},
                {"ep15_div1.asn1", DEPPRECATED_SEP},
                {"ep15_div2.asn1", DEPPRECATED_SEP},
                {"ep15_div3.asn1", DEPPRECATED_SEP}
        });
    }

    @Test
    public void sanityCheck() throws IOException {
        var bytes = resourceToByteArray("/v1/happy_flow/" + fileName);

        assertNotNull(bytes);
        assertNotEquals(0, bytes.length);

        // Dump using BC for failing cases
        var inputAsn1 = ASN1Primitive.fromByteArray(bytes);
        var inputDump = ASN1Dump.dumpAsString(inputAsn1);

        Asn1Envelope mapped = null;
        try {
            mapped = typeName.apply(bytes);
        } catch (PepRuntimeException cause) {
            Assert.fail(fileName + ":" + cause.getMessage() + "\n" + inputDump);
        }
        var encoded = mapped.encodeByteArray();

        // Should not be the same array, preferably encoding is actualy performed
        assertNotSame("Encoded array should not be the same array as the input", bytes, encoded);

        // The encoding should match the input assuming the input is DER-encoded
        var encodedAsn1 = ASN1Primitive.fromByteArray(encoded);
        var encodedDump = ASN1Dump.dumpAsString(encodedAsn1);

        assertEquals("Content should match structurally", inputDump, encodedDump);
        assertArrayEquals("Content should match bytewise", bytes, encoded);

        var pepRecipientKeyId = mapped.asAsn1RecipientKeyId();
        assertEquals(ONE, pepRecipientKeyId.getSchemeVersion());

        var schemeKeySetVersion = pepRecipientKeyId.getSchemeKeySetVersion();
        var recipient = pepRecipientKeyId.getRecipient();
        var recipientKeySetVersion = pepRecipientKeyId.getRecipientKeySetVersion();

        switch (typeName) {
            case DEPPRECATED_SEI: {
                var iddKey = eiDecryptionFromResource("/v1/keys/idd_sksv_" + schemeKeySetVersion + "_oin_" + recipient + "_kv_" + recipientKeySetVersion + ".pem");
                var ippKey = readSchemePublicKeyValueFromResource("/v1/keys/ipp_sksv_" + schemeKeySetVersion + "_kv_" + schemeKeySetVersion + ".pem");
                var signedData = mapped.asn1SignedData();
                var verificationKey = newSchnorrVerificationKey(ippKey, iddKey.getValue());
                assertTrue("DEPPRECATED_SEI verification failed", PepEcSchnorrVerification.verify(signedData, verificationKey, mapped.asn1Signature()));

                // Decode/reshuffle to pseudonym
                var decryptedInput = eiDecryption(mapped.asn1EcPointTriplet(), iddKey);
                var decodedEcPoint = PepIdentityOaepDecoder.oaepDecode(decryptedInput);
                var decodedIdentity = new PepIdentityOaepDecoded(decodedEcPoint);

                assertNotNull(decodedIdentity.getIdentifier());
                break;
            }
            case DEPPRECATED_SEP: {
                var pcdKey = epClosingFromResource("/v1/keys/pcd_sksv_" + schemeKeySetVersion + "_oin_" + recipient + "_ckv_" + recipientKeySetVersion + ".pem");
                var pddKey = epDecryptionFromResource("/v1/keys/pdd_sksv_" + schemeKeySetVersion + "_oin_" + recipient + "_kv_" + recipientKeySetVersion + ".pem");
                var pppKey = readSchemePublicKeyValueFromResource("/v1/keys/ppp_sksv_" + schemeKeySetVersion + "_kv_" + schemeKeySetVersion + ".pem");
                var signedData = mapped.asn1SignedData();
                var verificationKey = newSchnorrVerificationKey(pppKey, pddKey.getValue());
                assertTrue("DEPPRECATED_SEP verification failed", PepEcSchnorrVerification.verify(signedData, verificationKey, mapped.asn1Signature()));

                // Decode/reshuffle to pseudonym
                var reshuffeldDecryptedInput = epDecryption(mapped.asn1EcPointTriplet(), pcdKey, pddKey);
                var pseudonymValue = TestPepEcPoint.toBase64(reshuffeldDecryptedInput);

                assertNotNull(pseudonymValue);
                break;
            }
            case SDEP: {
                var pcdKey = epClosingFromResource("/v1/keys/pcd_sksv_" + schemeKeySetVersion + "_oin_" + recipient + "_ckv_" + recipientKeySetVersion + ".pem");
                var pddKey = epDecryptionFromResource("/v1/keys/pdd_sksv_" + schemeKeySetVersion + "_oin_" + recipient + "_kv_" + recipientKeySetVersion + ".pem");
                var signingKeyVersion = ((Asn1SignedDirectEncryptedPseudonymEnvelope) mapped).asn1SigningKeyVersion();
                var uKey = readSchemePublicKeyValueFromResource("/v1/keys/u_sksv_" + schemeKeySetVersion + "_kv_" + signingKeyVersion + ".pem");
                var signedData = mapped.asn1SignedData();
                var verificationKey = newPublicVerificationKey(uKey);
                assertTrue("SDEP verification failed", PepEcdsaVerification.verify(signedData, verificationKey, mapped.asn1Signature()));

                // Decode/reshuffle to pseudonym
                var reshuffeldDecryptedInput = epDecryption(mapped.asn1EcPointTriplet(), pcdKey, pddKey);
                var pseudonymValue = TestPepEcPoint.toBase64(reshuffeldDecryptedInput);

                assertNotNull(pseudonymValue);
                break;
            }
            default:
                Assert.fail(typeName.toString());
        }
    }

    enum Asn1Type
            implements Function<byte[], Asn1Envelope> {
        SDEP(Asn1SignedDirectEncryptedPseudonymEnvelope::fromByteArray),
        DEPPRECATED_SEI(Asn1DeprecatedSignedEncryptedIdentityEnvelope::fromByteArray),
        DEPPRECATED_SEP(Asn1DeprecatedSignedEncryptedPseudonymEnvelope::fromByteArray),
        ;

        private final Function<byte[], Asn1Envelope> mapper;

        Asn1Type(Function<byte[], Asn1Envelope> mapper) {
            this.mapper = mapper;
        }

        @Override
        public Asn1Envelope apply(byte[] bytes) {
            return mapper.apply(bytes);
        }

    }

}

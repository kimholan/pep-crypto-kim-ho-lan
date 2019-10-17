package nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym;

import nl.logius.pepcrypto.lib.asn1.TestAsn1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class Asn1SignedDirectEncryptedPseudonymParameterizedTest {

    @Parameter(0)
    public String resource;

    @Parameter(1)
    public String recipientKeyId;

    @Parameter(2)
    public String schemePublicKeyResource;

    @Parameter(3)
    public String drkiResource;

    @Parameter(4)
    public String pddResource;

    @Parameter(5)
    public String pcdResource;

    @Parameter(6)
    public String pseudonymValue;

    @Parameter(7)
    public String diversifier;

    @Parameters
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {
                        "/v1/happy_flow/dep12.asn1",
                        "1:1:99990030000000000001:20170916",
                        "/v1/keys/u_sksv_1_kv_1.pem",
                        "/v1/keys/drki_sksv_1_oin_99990030000000000001_kv_20170916.pem",
                        "/v1/keys/pdd_sksv_1_oin_99990030000000000001_kv_20170916.pem",
                        "/v1/keys/pcd_sksv_1_oin_99990030000000000001_ckv_20170916.pem",
                        "BEmEs7C/KFZpaALZ2YjdTBx5RPGkAMFWNN6gh71p/LaALbtyOxzLFNdDJd55DqDVkralRLtU7o5St3bgzhlerOSqp8fDAjyoRK4tN/seN5Oh",
                        null,
                },

        });
    }

    @Test
    public void verifyStructureAndDecryption() {
        var actual = Optional.of(resource)
                             .map(TestAsn1::assertSignedDirectEncryptedPseudonym)
                             .map(TestAsn1::assertPseudonymTypeBsn)
                             .map(it -> TestAsn1.assertEcdsaVerification(it, schemePublicKeyResource))
                             .map(it -> TestAsn1.assertRecipientKeyId(it, recipientKeyId))
                             .map(it -> TestAsn1.assertDiversifier(it, diversifier))
                             .map(it -> TestAsn1.directPseudonymDecryption(it, drkiResource, pddResource, pcdResource))
                             .orElse(null);

        assertEquals(pseudonymValue, actual);
    }

}

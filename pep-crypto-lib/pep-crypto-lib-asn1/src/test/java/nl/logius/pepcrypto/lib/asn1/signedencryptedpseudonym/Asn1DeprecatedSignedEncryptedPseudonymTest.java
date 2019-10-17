package nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym;

import nl.logius.pepcrypto.lib.asn1.TestAsn1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class Asn1DeprecatedSignedEncryptedPseudonymTest {

    @Parameter(0)
    public String resource;

    @Parameter(1)
    public String recipientKeyId;

    @Parameter(2)
    public String schemePublicKeyResource;

    @Parameter(3)
    public String pddResource;

    @Parameter(4)
    public String pcdResource;

    @Parameter(5)
    public String pseudonymValue;

    @Parameter(6)
    public String diversifier;

    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {
                        "/v1/happy_flow/ep08.asn1",
                        "1:10:99990020000000000001:20211207",
                        "/v1/keys/ppp_sksv_10_kv_10.pem",
                        "/v1/keys/pdd_sksv_10_oin_99990020000000000001_kv_20211207.pem",
                        "/v1/keys/pcd_sksv_10_oin_99990020000000000001_ckv_20211207.pem",
                        "BIkIDIGkP3EkhyJ+pnEJHtOAhqg5kJmX6BeEFZaQCi0zszb9INrwqdyFbvCAZ3DTU5tUNobhmfJId/ElgP5b0Js367EzXrJZhwPPUWqA+Mjx",
                        null,
                },

        });
    }

    @Test
    public void verifyStructureAndDecryption() {
        var actual = Optional.of(resource)
                             .map(TestAsn1::assertDeprecatedSignedEncryptedPseudonym)
                             .map(TestAsn1::assertPseudonymTypeEuid)
                             .map(it -> TestAsn1.assertEcSchnorrVerification(it, schemePublicKeyResource, pddResource))
                             .map(it -> TestAsn1.assertRecipientKeyId(it, recipientKeyId))
                             .map(it -> TestAsn1.assertDiversifier(it, diversifier))
                             .map(it -> TestAsn1.pseudonymDecryption(it, pddResource, pcdResource))
                             .orElse(null);

        assertEquals(pseudonymValue, actual);
    }

}




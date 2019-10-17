package nl.logius.pepcrypto.lib.asn1.signedencryptedidentity;

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
public class Asn1DeprecatedSignedEncryptedIdentityParameterizedTest {

    @Parameter(0)
    public String resource;

    @Parameter(1)
    public String recipientKeyId;

    @Parameter(2)
    public String schemePublicKeyResource;

    @Parameter(3)
    public String iddResource;

    @Parameter(4)
    public String decodedIdentity;

    @Parameters
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {
                        "/v1/happy_flow/ei04.asn1",
                        "1:1:99990020000000000001:20180420",
                        "/v1/keys/ipp_sksv_1_kv_1.pem",
                        "/v1/keys/idd_sksv_1_oin_99990020000000000001_kv_20180420.pem",
                        "799890133:01:B:9",
                },

        });
    }

    @Test
    public void verifyStructureAndDecryption() {
        var actual = Optional.of(resource)
                             .map(TestAsn1::assertDeprecatedSignedEncryptedIdentity)
                             .map(it -> TestAsn1.assertEcSchnorrVerification(it, schemePublicKeyResource, iddResource))
                             .map(it -> TestAsn1.assertRecipientKeyId(it, recipientKeyId))
                             .map(it -> TestAsn1.identityDecryption(it, iddResource))
                             .orElse(null);

        assertEquals(decodedIdentity, actual);
    }

}




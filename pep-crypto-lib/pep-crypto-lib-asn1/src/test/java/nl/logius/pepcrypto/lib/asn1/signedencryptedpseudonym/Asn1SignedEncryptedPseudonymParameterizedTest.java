package nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym;

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
public class Asn1SignedEncryptedPseudonymParameterizedTest {

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

    @Parameters
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {
                        "/legacy/ep02.asn1",
                        "1:1:99990020000000000002:20191103",
                        "/v1/keys/ppp_sksv_1_kv_1.pem",
                        "/v1/keys/pdd_sksv_1_oin_99990020000000000002_kv_20191103.pem",
                        "/v1/keys/pcd_sksv_1_oin_99990020000000000002_ckv_20191103.pem",
                        "BKrjGwsfDGfDrdcqwyZCP1SG/INKhqkTn3KYRF19U9XvOq5UN+QLo7Nitd6pOhss076VegCJEHJdobMKaZ3sjgfIQ0fOIzyIl3J4OeY6WxXB",
                        null,
                },
                {
                        "/legacy/ep02_div1.asn1",
                        "1:1:99990020000000000002:20191103",
                        "/v1/keys/ppp_sksv_1_kv_1.pem",
                        "/v1/keys/pdd_sksv_1_oin_99990020000000000002_kv_20191103.pem",
                        "/v1/keys/pcd_sksv_1_oin_99990020000000000002_ckv_20191103.pem",
                        "BGp/TnUFq1xACKBPO0SrFu7sE5myIyJnHmeXNxRg1VxGGyC6uEi0TL/FMvlCr+LRy0Qy3NBL/a4rlVMOIWcAwx94+eo67C8Ak9+ypU9ktsgx",
                        "[generated.asn1.DiversifierKeyValuePair(Role, Company)]",
                },
                {
                        "/legacy/ep02_div2.asn1",
                        "1:1:99990020000000000002:20191103",
                        "/v1/keys/ppp_sksv_1_kv_1.pem",
                        "/v1/keys/pdd_sksv_1_oin_99990020000000000002_kv_20191103.pem",
                        "/v1/keys/pcd_sksv_1_oin_99990020000000000002_ckv_20191103.pem",
                        "BGR0juEt56fS+clw0mHEddRc0FnwlobYVuGymB4gTUNZ1WFAwhLA4PoLuqF/p8p8MykgPWKB3ie8tl7aukuGIpkKV+6ygVLpzbYV7aQHnW+y",
                        "[generated.asn1.DiversifierKeyValuePair(Function, Controller), generated.asn1.DiversifierKeyValuePair(Group, Finance)]",
                },
                {
                        "/legacy/ep02_div3.asn1",
                        "1:1:99990020000000000002:20191103",
                        "/v1/keys/ppp_sksv_1_kv_1.pem",
                        "/v1/keys/pdd_sksv_1_oin_99990020000000000002_kv_20191103.pem",
                        "/v1/keys/pcd_sksv_1_oin_99990020000000000002_ckv_20191103.pem",
                        "BMQMEoXGyLNJqvsu+waDRMjiS9skd3nLZOaIbfyxuK36N9sDMJc6w8EWFnvbhuARLpzJRo9FZPBGS2lk2RaFmJjeeJbNax7WLmOaPGNs9JG9",
                        "[generated.asn1.DiversifierKeyValuePair(O, Logius), generated.asn1.DiversifierKeyValuePair(R, Tester), generated.asn1.DiversifierKeyValuePair(U, Bsnk)]",
                },

        });
    }

    @Test
    public void verifyStructureAndDecryption() {
        var actual = Optional.of(resource)
                             .map(TestAsn1::assertSignedEncryptedPseudonym)
                             .map(TestAsn1::assertPseudonymTypeBsn)
                             .map(it -> TestAsn1.assertEcsdsaVerification(it, schemePublicKeyResource, pddResource))
                             .map(it -> TestAsn1.assertRecipientKeyId(it, recipientKeyId))
                             .map(it -> TestAsn1.assertDiversifier(it, diversifier))
                             .map(it -> TestAsn1.pseudonymDecryption(it, pddResource, pcdResource))
                             .orElse(null);

        assertEquals(pseudonymValue, actual);
    }

}




package nl.logius.pepcrypto.lib.asn1.testdata;

import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;
import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepDecryptedPseudonymClosingKeyConversion;
import nl.logius.pepcrypto.lib.crypto.key.PepDpClosingKeyConversionClosingPrivateKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Base64;
import java.util.List;

import static nl.logius.pepcrypto.lib.TestPemObjects.epClosingFromResource;
import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestdataPseudonymConversionTest {

    @Parameter(0)
    public String sourcePseudonym;

    @Parameter(1)
    public String targetPseudonym;

    @Parameters
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {"/v1/pseudonyms/p_01.asn1", "/v1/pseudonyms/p_01.asn1"},
                {"/v1/pseudonyms/p_16.asn1", "/v1/pseudonyms/p_07.asn1"},
                {"/v1/pseudonyms/p_17.asn1", "/v1/pseudonyms/p_03.asn1"},
        });
    }

    @Test
    public void convertSourceToTargetClosingKey() {
        assertClosingKeyConversion(sourcePseudonym, targetPseudonym);
    }

    private void assertClosingKeyConversion(String sourceResource, String targetResource) {
        // Read the reference source pseudonym and corresponding private closing key
        var sourcePseudonymBytes = resourceToByteArray(sourceResource);
        var sourcePseudonym = Asn1DecryptedPseudonym.fromByteArray(sourcePseudonymBytes);
        var sourcePseudonymValueBase64 = sourcePseudonym.getPseudonymValue();
        var sourcePseudonymValue = Base64.getDecoder().decode(sourcePseudonymValueBase64);
        var sourceEcPoint = PepCrypto.decodeEcPoint(sourcePseudonymValue);

        var sourceClosingKeyResource = getClosingKeyResourceName(sourcePseudonym);
        var sourceClosingKey = epClosingFromResource(sourceClosingKeyResource);

        // Read the reference target pseudonym and corresponding private closing key
        var targetPseudonymBytes = resourceToByteArray(targetResource);
        var targetPseudonym = Asn1DecryptedPseudonym.fromByteArray(targetPseudonymBytes);
        var expectedTargetPseudonymValue = targetPseudonym.getPseudonymValue();

        var targetClosingKeyResource = getClosingKeyResourceName(targetPseudonym);
        var targetClosingKey = epClosingFromResource(targetClosingKeyResource);

        // Perform conversion using the key values and reference source pseudonym
        var closingKeyConversionKey = PepDpClosingKeyConversionClosingPrivateKey.newInstance(sourceClosingKey, targetClosingKey);
        var actualTargetPseudonym = PepDecryptedPseudonymClosingKeyConversion.dpClosingKeyConversion(sourceEcPoint, closingKeyConversionKey);

        // Assert that computed pseudonym value from conversion matches the reference value
        var actualTargetPseudonymEncoded = actualTargetPseudonym.getEncoded(false);
        var actualTargetPseudonymValue = Base64.getEncoder().encodeToString(actualTargetPseudonymEncoded);

        assertEquals(actualTargetPseudonymValue, expectedTargetPseudonymValue);
    }

    private String getClosingKeyResourceName(Asn1DecryptedPseudonym targetPseudonym) {
        var targetSchemeKeySetVersion = targetPseudonym.getSchemeKeySetVersion();
        var targetOin = targetPseudonym.getRecipient();
        var targetRecipientKeySetVersion = targetPseudonym.getRecipientKeySetVersion();

        return "/v1/keys/pcd_sksv_" + targetSchemeKeySetVersion + "_oin_" + targetOin + "_ckv_" + targetRecipientKeySetVersion + ".pem";
    }

}

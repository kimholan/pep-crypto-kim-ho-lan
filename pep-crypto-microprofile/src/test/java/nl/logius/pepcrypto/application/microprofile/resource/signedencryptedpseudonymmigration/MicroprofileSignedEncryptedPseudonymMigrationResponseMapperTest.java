package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedpseudonymmigration;

import generated.asn1.Pseudonym;
import nl.logius.pepcrypto.lib.TestPepEcPoint;
import nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym.Asn1SignedEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedPseudonymDecryption;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.util.Base64;

import static nl.logius.pepcrypto.lib.TestPemObjects.epClosingFromResource;
import static nl.logius.pepcrypto.lib.TestPemObjects.epDecryptionFromResource;
import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofileSignedEncryptedPseudonymMigrationResponseMapperTest {

    @InjectMocks
    private MicroprofileSignedEncryptedPseudonymMigrationResponseMapper mapper;

    @Test
    public void mapToResponse() {
        var exchange = mock(MicroprofileSignedEncryptedPseudonymMigrationExchange.class);

        //  mappedInput
        var rawInput = resourceToByteArray("/legacy/ep02.asn1");
        var mappedInput = Asn1SignedEncryptedPseudonymEnvelope.fromByteArray(rawInput);
        when(exchange.getMappedInput()).thenReturn(mappedInput);

        // Decode/reshuffle to pseudonym
        var pddKey = epDecryptionFromResource("/v1/keys/pdd_sksv_1_oin_99990020000000000002_kv_20191103.pem");
        var pcdKey = epClosingFromResource("/v1/keys/pcd_sksv_1_oin_99990020000000000002_ckv_20191103.pem");
        var reshuffeldDecryptedInput = PepEncryptedPseudonymDecryption.epDecryption(mappedInput.asn1EcPointTriplet(), pcdKey, pddKey);
        var pseudonymValue = TestPepEcPoint.toBase64(reshuffeldDecryptedInput);

        assertEquals("BKrjGwsfDGfDrdcqwyZCP1SG/INKhqkTn3KYRF19U9XvOq5UN+QLo7Nitd6pOhss076VegCJEHJdobMKaZ3sjgfIQ0fOIzyIl3J4OeY6WxXB", pseudonymValue);

        // Decrypted pseudonym
        var reshuffledDecryptedEcPointEncoded = Base64.getEncoder().encodeToString(reshuffeldDecryptedInput.getEncoded(false));
        var pepRecipientKeyId = mappedInput.asAsn1RecipientKeyId();
        var recipientKeySetVersion = pepRecipientKeyId.getRecipientKeySetVersion();

        var pepDecryptedPseudonym = Pseudonym.builder()
                                             .schemeVersion(pepRecipientKeyId.getSchemeVersion())
                                             .schemeKeySetVersion(pepRecipientKeyId.getSchemeKeySetVersion())
                                             .recipient(pepRecipientKeyId.getRecipient())
                                             .recipientKeySetVersion(recipientKeySetVersion)
                                             .type(mappedInput.asn1PseudonymType())
                                             .pseudonymValue(reshuffledDecryptedEcPointEncoded)
                                             .build();
        when(exchange.asAsn1DecryptedPseudonym()).thenReturn(pepDecryptedPseudonym);

        mapper.mapToResponse(exchange);
    }

}

package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedpseudonymmigration;

import generated.asn1.Pseudonym;
import nl.logius.pepcrypto.lib.TestPepEcPoint;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepDirectEncryptedPseudonymDecryption;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepEncryptedPseudonymDecryption;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.util.Base64;

import static nl.logius.pepcrypto.lib.TestPemObjects.drkiFromResource;
import static nl.logius.pepcrypto.lib.TestPemObjects.epClosingFromResource;
import static nl.logius.pepcrypto.lib.TestPemObjects.epDecryptionFromResource;
import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofileSignedDirectEncryptedPseudonymMigrationResponseMapperTest {

    @InjectMocks
    private MicroprofileSignedDirectEncryptedPseudonymMigrationResponseMapper mapper;

    @Test
    public void mapToResponse() {
        var exchange = mock(MicroprofileSignedDirectEncryptedPseudonymMigrationExchange.class);

        //  mappedInput
        var rawInput = resourceToByteArray("/v1/happy_flow/dep12.asn1");
        var mappedInput = Asn1SignedDirectEncryptedPseudonymEnvelope.fromByteArray(rawInput);
        when(exchange.getMappedInput()).thenReturn(mappedInput);

        // Decode/reshuffle to pseudonym
        var drkKey = drkiFromResource("/v1/keys/drki_sksv_1_oin_99990030000000000001_kv_20170916.pem");
        var pddKey = epDecryptionFromResource("/v1/keys/pdd_sksv_1_oin_99990030000000000001_kv_20170916.pem");
        var pcdKey = epClosingFromResource("/v1/keys/pcd_sksv_1_oin_99990030000000000001_ckv_20170916.pem");
        var encryptedEcPoint = PepDirectEncryptedPseudonymDecryption.depDecryption(mappedInput.asn1EcPointTriplet(), drkKey);
        var reshuffeldDecryptedInput = PepEncryptedPseudonymDecryption.epDecryption(encryptedEcPoint, pcdKey, pddKey);
        var pseudonymValue = TestPepEcPoint.toBase64(reshuffeldDecryptedInput);

        assertEquals("BEmEs7C/KFZpaALZ2YjdTBx5RPGkAMFWNN6gh71p/LaALbtyOxzLFNdDJd55DqDVkralRLtU7o5St3bgzhlerOSqp8fDAjyoRK4tN/seN5Oh", pseudonymValue);

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

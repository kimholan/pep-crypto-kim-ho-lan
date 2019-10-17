package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedidentity;

import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope;
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoded;
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoder;
import nl.logius.pepcrypto.lib.crypto.algorithm.PepDirectEncryptedIdentityDecryption;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static nl.logius.pepcrypto.lib.TestPemObjects.eiDecryptionFromResource;
import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofileSignedDirectEncryptedIdentityResponseMapperTest {

    @InjectMocks
    private MicroprofileSignedDirectEncryptedIdentityResponseMapper mapper;

    @Test
    public void mapToResponse() {
        var exchange = mock(MicroprofileSignedDirectEncryptedIdentityExchange.class);

        //  mappedInput
        var rawInput = resourceToByteArray("/v2/happy_flow/dei02.asn1");
        var mappedInput = Asn1SignedDirectEncryptedIdentityv2Envelope.fromByteArray(rawInput);
        when(exchange.getMappedInput()).thenReturn(mappedInput);

        // decrypt
        var iddKey = eiDecryptionFromResource("/v2/keys/idd_sksv_1_oin_99990020000000000002_kv_20191103.pem");
        var decryptedEcPoint = PepDirectEncryptedIdentityDecryption.deiDecryption(mappedInput.asn1EcPointTriplet(), iddKey);
        var decoded = PepIdentityOaepDecoder.oaepDecode(decryptedEcPoint);
        var decodedIdentity = new PepIdentityOaepDecoded(decoded);

        assertEquals("799890133", decodedIdentity.getIdentifier());

        // Decrypted pseudonym
        when(exchange.getDecryptedEcPoint()).thenReturn(decryptedEcPoint);

        mapper.mapToResponse(exchange);
    }

}

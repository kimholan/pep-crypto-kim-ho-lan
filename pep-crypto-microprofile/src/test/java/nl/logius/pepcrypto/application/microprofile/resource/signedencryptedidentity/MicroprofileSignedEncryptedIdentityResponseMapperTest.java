package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedidentity;

import nl.logius.pepcrypto.lib.TestResources;
import nl.logius.pepcrypto.lib.asn1.signedencryptedidentity.Asn1SignedEncryptedIdentityEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepIdentityOaepDecoded;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofileSignedEncryptedIdentityResponseMapperTest {

    @InjectMocks
    private MicroprofileSignedEncryptedIdentityResponseMapper mapper;

    @Test
    public void mapToResponse() {
        var seiResource = "/legacy/ei02.asn1";
        var rawInput = TestResources.resourceToByteArray(seiResource);
        var mappedInput = Asn1SignedEncryptedIdentityEnvelope.fromByteArray(rawInput);

        var exchange = mock(MicroprofileSignedEncryptedIdentityExchange.class);
        var decodedIdentity = mock(PepIdentityOaepDecoded.class);

        when(exchange.getMappedInput()).thenReturn(mappedInput);
        when(exchange.getDecodedIdentity()).thenReturn(decodedIdentity);

        mapper.mapToResponse(exchange);
    }

}

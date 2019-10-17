package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_10_2;

import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class ApiSignedDirectEncryptedIdentityv2MapperTest {

    @InjectMocks
    private ApiSignedDirectEncryptedIdentityv2Mapper mapper;

    @Test
    public void map() {
        var bytes = resourceToByteArray("/v2/happy_flow/dei02.asn1");
        var mappable = mock(ApiExchange.class);

        when(mappable.getRawInput()).thenReturn(bytes);

        mapper.processRawInput(mappable);

        verify(mappable).getRawInput();
        verify(mappable).setMappedInput(any(Asn1SignedDirectEncryptedIdentityv2Envelope.class));
    }

}

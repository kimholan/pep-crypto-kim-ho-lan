package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_4;

import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;
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
public class ApiDeprecatedSignedEncryptedPseudonymAsn1MapperTest {

    @InjectMocks
    private ApiDeprecatedSignedEncryptedPseudonymAsn1Mapper mapper;

    @Test
    public void map() {
        var bytes = resourceToByteArray("/v1/happy_flow/ep08.asn1");
        var mappable = mock(ApiExchange.class);

        when(mappable.getRawInput()).thenReturn(bytes);

        mapper.processRawInput(mappable);

        verify(mappable).getRawInput();
        verify(mappable).setMappedInput(any(Asn1PseudonymEnvelope.class));
    }

}

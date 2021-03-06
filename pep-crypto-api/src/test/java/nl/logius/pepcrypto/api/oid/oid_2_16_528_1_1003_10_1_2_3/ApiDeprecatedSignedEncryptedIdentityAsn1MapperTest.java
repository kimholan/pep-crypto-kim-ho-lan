package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_3;

import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.lib.asn1.signedencryptedidentity.Asn1DeprecatedSignedEncryptedIdentityEnvelope;
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
public class ApiDeprecatedSignedEncryptedIdentityAsn1MapperTest {

    @InjectMocks
    private ApiDeprecatedSignedEncryptedIdentityAsn1Mapper mapper;

    @Test
    public void map() {
        var bytes = resourceToByteArray("/v1/happy_flow/ei04.asn1");
        var mappable = mock(ApiExchange.class);

        when(mappable.getRawInput()).thenReturn(bytes);

        mapper.processRawInput(mappable);

        verify(mappable).getRawInput();
        verify(mappable).setMappedInput(any(Asn1DeprecatedSignedEncryptedIdentityEnvelope.class));
    }

}

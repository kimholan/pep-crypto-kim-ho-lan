package nl.logius.pepcrypto.application.microprofile.resource.pseudonymconversion;

import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofilePseudonymConversionResponseMapperTest {

    @InjectMocks
    private MicroprofilePseudonymConversionResponseMapper mapper;

    @Test
    public void mapToResponse() {
        var exchange = mock(MicroprofilePseudonymConversionExchange.class);

        //  mappedInput
        var rawInput = resourceToByteArray("/v1/pseudonyms/p01_div1.asn1");
        var mappedInput = Asn1DecryptedPseudonym.fromByteArray(rawInput);
        when(exchange.getMappedInput()).thenReturn(mappedInput);

        when(exchange.asAsn1DecryptedPseudonym()).thenReturn(mappedInput);

        mapper.mapToResponse(exchange);

    }

}

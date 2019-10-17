package nl.logius.pepcrypto.application.microprofile.resource.pseudonymconversion;

import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofilePseudonymClosingKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileSchemeKeysParser;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileServiceProviderKeysParser;
import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofilePseudonymConversionKeyStoreTest {

    @InjectMocks
    private MicroprofilePseudonymConversionKeyStore target;

    @Mock
    private MicroprofileSchemeKeysParser schemeKeysParser;

    @Mock
    private MicroprofileServiceProviderKeysParser serviceProviderKeysParser;

    @Mock
    private MicroprofilePseudonymClosingKeySelector closingKeySelector;

    @Test
    public void parseServiceProviderKeys() {
        var exchange = mock(MicroprofilePseudonymConversionExchange.class);

        var k1 = mock(PemEcPrivateKey.class);
        var k2 = mock(PemEcPrivateKey.class);
        var list = List.of(k1, k2);

        when(exchange.getParsedServiceProviderKeys()).thenReturn(list);

        var mappedInput = mock(Asn1DecryptedPseudonym.class);
        when(exchange.getMappedInput()).thenReturn(mappedInput);

        target.processKeySelection(exchange);
    }

}

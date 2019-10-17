package nl.logius.pepcrypto.application.microprofile.resource.pseudonymconversion;

import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymRequest;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class MicroprofilePseudonymConversionExchangeTest {

    @Test
    public void coverage() {
        var request = mock(OASPseudonymRequest.class);
        var exchange = new MicroprofilePseudonymConversionExchange(request);

        exchange.getRawInput();

        exchange.setMappedInput(null);
        exchange.getMappedInput();
        exchange.getRawServiceProviderKeys();
        exchange.getParsedServiceProviderKeys();
        exchange.setParsedServiceProviderKeys(null);
        exchange.setSourceClosingKey(null);

        var key = mock(PemEcPrivateKey.class);
        exchange.setSourceClosingKey(key);
        exchange.setTargetClosingKey(key);
        exchange.setConvertedEcPoint(null);
        exchange.getDecryptedPseudonymResultPseudonymValue();
        exchange.setTargetClosingKey(null);
    }

    @Test(expected = NullPointerException.class)
    public void coverageNpe() {
        var request = mock(OASPseudonymRequest.class);
        var exchange = new MicroprofilePseudonymConversionExchange(request);
        exchange.getTargetClosingKeyAsRecipientKeyId();
    }

}


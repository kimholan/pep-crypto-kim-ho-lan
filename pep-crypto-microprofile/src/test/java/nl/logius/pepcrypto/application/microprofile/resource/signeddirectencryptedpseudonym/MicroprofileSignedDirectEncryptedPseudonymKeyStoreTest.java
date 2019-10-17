package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedpseudonym;

import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileDirectReceiveKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofilePseudonymClosingKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofilePseudonymDecryptionKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileSchemeKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileSchemeKeysParser;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileServiceProviderKeysParser;
import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofileSignedDirectEncryptedPseudonymKeyStoreTest {

    @InjectMocks
    private MicroprofileSignedDirectEncryptedPseudonymKeyStore target;

    @Mock
    private MicroprofileSchemeKeysParser schemeKeysParser;

    @Mock
    private MicroprofileServiceProviderKeysParser serviceProviderKeysParser;

    @Mock
    private MicroprofilePseudonymClosingKeySelector closingKeySelector;

    @Mock
    private MicroprofilePseudonymDecryptionKeySelector decryptionKeySelector;

    @Mock
    private MicroprofileSchemeKeySelector schemeKeySelector;

    @Mock
    private MicroprofileDirectReceiveKeySelector directReceiveKeySelector;

    @Test
    public void parseServiceProviderKeys() {
        var exchange = mock(MicroprofileSignedDirectEncryptedPseudonymExchange.class);

        var k1 = mock(PemEcPrivateKey.class);
        var k2 = mock(PemEcPrivateKey.class);
        var list = List.of(k1, k2);

        when(exchange.getParsedServiceProviderKeys()).thenReturn(list);

        var schemeKeys = Map.of(
                "urn:nl-gdi-eid:1.0:pp-key:ENV:012345012345012345012345:PP_P:20190101",
                PepCrypto.getBasePoint()
        );
        when(exchange.getParsedSchemeKeys()).thenReturn(schemeKeys);

        target.processKeySelection(exchange);
    }

}

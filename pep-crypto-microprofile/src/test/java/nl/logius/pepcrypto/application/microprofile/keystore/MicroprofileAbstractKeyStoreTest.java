package nl.logius.pepcrypto.application.microprofile.keystore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static org.mockito.Mockito.mock;

@RunWith(StrictStubs.class)
public class MicroprofileAbstractKeyStoreTest
        extends MicroprofileAbstractKeyStore {

    @InjectMocks
    private MicroprofileAbstractKeyStoreTest target;

    @Mock
    private MicroprofileSchemeKeysParser schemeKeysParser;

    @Mock
    private MicroprofileServiceProviderKeysParser serviceProviderKeysParser;

    @Test
    public void parseServiceProviderKeys() {
        var exchange = mock(MicroprofileDecryptionKeystoreExchange.class);

        target.parseServiceProviderKeys(exchange);
    }

    @Test
    public void parseSchemeKeys() {
        var exchange = mock(MicroprofileDecryptionKeystoreExchange.class);

        target.parseSchemeKeys(exchange);
    }

    @Test
    public void requireValidSchemeKeyNames() {
        var exchange = mock(MicroprofileDecryptionKeystoreExchange.class);

        target.requireValidSchemeKeyNames(exchange);
    }

    @Test
    public void requireValidSchemeKeyStructure() {
        var exchange = mock(MicroprofileDecryptionKeystoreExchange.class);

        target.requireValidSchemeKeyStructure(exchange);
    }

}

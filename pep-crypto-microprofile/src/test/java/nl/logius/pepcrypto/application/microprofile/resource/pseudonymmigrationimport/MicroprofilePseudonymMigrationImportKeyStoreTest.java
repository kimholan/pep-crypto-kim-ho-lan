package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationimport;

import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileMigrationTargetKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileSchemeKeysParser;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileServiceProviderKeysParser;
import nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym;
import nl.logius.pepcrypto.lib.asn1.recipientkeyid.Asn1RecipientKeyId;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofilePseudonymMigrationImportKeyStoreTest {

    @InjectMocks
    private MicroprofilePseudonymMigrationImportKeyStore target;

    @Mock
    private MicroprofileSchemeKeysParser schemeKeysParser;

    @Mock
    private MicroprofileServiceProviderKeysParser serviceProviderKeysParser;

    @Mock
    private MicroprofileMigrationTargetKeySelector targetKeySelector;

    @Test
    public void parseServiceProviderKeys() {
        var exchange = mock(MicroprofilePseudonymMigrationImportExchange.class);

        var k1 = mock(PemEcPrivateKey.class);
        var k2 = mock(PemEcPrivateKey.class);
        var list = List.of(k1, k2);

        when(exchange.getParsedServiceProviderKeys()).thenReturn(list);

        var mappedInput = mock(Asn1MigrationIntermediaryPseudonym.class);
        when(exchange.getMappedInput()).thenReturn(mappedInput);

        var targetRecipientKeyId = mock(Asn1RecipientKeyId.class);
        when(mappedInput.asTargetAsn1RecipientKeyId()).thenReturn(targetRecipientKeyId);
        when(mappedInput.getSource()).thenReturn("source");
        when(mappedInput.getSourceKeySetVersion()).thenReturn(BigInteger.valueOf(909L));

        target.processKeySelection(exchange);
    }

}

package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationexport;

import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileMigrationSourceKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileSchemeKeysParser;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileServiceProviderKeysParser;
import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;
import nl.logius.pepcrypto.lib.asn1.recipientkeyid.Asn1RecipientKeyId;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofilePseudonymMigrationExportKeyStoreTest {

    @InjectMocks
    private MicroprofilePseudonymMigrationExportKeyStore target;

    @Mock
    private MicroprofileSchemeKeysParser schemeKeysParser;

    @Mock
    private MicroprofileServiceProviderKeysParser serviceProviderKeysParser;

    @Mock
    private MicroprofileMigrationSourceKeySelector sourceMigrationKeySelector;

    @Test
    public void parseServiceProviderKeys() {
        var exchange = mock(MicroprofilePseudonymMigrationExportExchange.class);

        var k1 = mock(PemEcPrivateKey.class);
        var k2 = mock(PemEcPrivateKey.class);
        var list = List.of(k1, k2);

        var mappedInput = mock(Asn1DecryptedPseudonym.class);

        when(exchange.getMappedInput()).thenReturn(mappedInput);
        when(exchange.getParsedServiceProviderKeys()).thenReturn(list);

        var asn1RecipientKeyId = mock(Asn1RecipientKeyId.class);
        when(mappedInput.asAsn1RecipientKeyId()).thenReturn(asn1RecipientKeyId);
        when(asn1RecipientKeyId.migrationId(any())).thenReturn(asn1RecipientKeyId);

        when(exchange.getRequestMigrationId()).thenReturn("requestMigrationId");
        when(exchange.getRequestTargetMigrant()).thenReturn("requestTargetMigrant");
        when(exchange.getRequestTargetMigrantKeySetVersion()).thenReturn(BigInteger.valueOf(101L));

        target.processKeySelection(exchange);
    }

}

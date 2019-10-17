package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedpseudonymmigration;

import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileAbstractKeyStore;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileMigrationSourceKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileMigrationTargetKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofilePseudonymClosingKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofilePseudonymDecryptionKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileSchemeKeySelector;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileSchemeKeysParser;
import nl.logius.pepcrypto.application.microprofile.keystore.MicroprofileServiceProviderKeysParser;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;
import nl.logius.pepcrypto.lib.asn1.recipientkeyid.Asn1RecipientKeyId;
import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofileSignedEncryptedPseudonymMigrationKeyStoreTest
        extends MicroprofileAbstractKeyStore {

    @InjectMocks
    private MicroprofileSignedEncryptedPseudonymMigrationKeyStore target;

    @Mock
    private MicroprofileSchemeKeysParser schemeKeysParser;

    @Mock
    private MicroprofileServiceProviderKeysParser serviceProviderKeysParser;

    @Mock
    private MicroprofilePseudonymDecryptionKeySelector decryptionKeySelector;

    @Mock
    private MicroprofilePseudonymClosingKeySelector closingKeySelector;

    @Mock
    private MicroprofileMigrationSourceKeySelector migrationSourceKeySelector;

    @Mock
    private MicroprofileMigrationTargetKeySelector migrationTargetKeySelector;

    @Mock
    private MicroprofileSchemeKeySelector schemeKeySelector;

    @Test
    public void parseServiceProviderKeys() {
        var exchange = mock(MicroprofileSignedEncryptedPseudonymMigrationExchange.class);

        var k1 = mock(PemEcPrivateKey.class);
        var k2 = mock(PemEcPrivateKey.class);
        var epms = mock(PemEcPrivateKey.class);
        var epmt = mock(PemEcPrivateKey.class);
        var list = List.of(k1, k2, epms, epmt);

        when(k1.getSourceOrRecipient()).thenReturn("source");
        when(k2.getSourceOrRecipient()).thenReturn("source");
        when(epms.getSourceOrRecipient()).thenReturn("source");
        when(epmt.getTargetOrRecipient()).thenReturn("target");

        when(exchange.getParsedServiceProviderKeys()).thenReturn(list);

        var schemeKeys = Map.of(
                "urn:nl-gdi-eid:1.0:pp-key:ENV:012345012345012345012345:PP_P:20190101",
                PepCrypto.getBasePoint()
        );
        when(exchange.getParsedSchemeKeys()).thenReturn(schemeKeys);

        var mappedInput = mock(Asn1PseudonymEnvelope.class);
        when(exchange.getMappedInput()).thenReturn(mappedInput);
        when(exchange.getMigrationSourceKey()).thenReturn(epms);

        var pepRecipientKeyId = mock(Asn1RecipientKeyId.class);
        doReturn(pepRecipientKeyId).when(pepRecipientKeyId).recipient(isNull());
        doReturn(pepRecipientKeyId).when(pepRecipientKeyId).recipientKeySetVersion(isNull(String.class));
        doReturn(pepRecipientKeyId).when(pepRecipientKeyId).migrationId(isNull());

        when(mappedInput.asAsn1RecipientKeyId()).thenReturn(pepRecipientKeyId);

        when(pepRecipientKeyId.getRecipient()).thenReturn("source");
        when(epms.getSourceOrRecipient()).thenReturn("source");
        when(epms.getTargetOrRecipient()).thenReturn("target");

        target.processKeySelection(exchange);
    }

}


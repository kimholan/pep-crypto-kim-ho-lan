package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedpseudonymmigration;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymMigrationRequest;
import nl.logius.pepcrypto.lib.TestResources;
import nl.logius.pepcrypto.lib.TestValues;
import nl.logius.pepcrypto.lib.asn1.signedencryptedpseudonym.Asn1SignedEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MicroprofileSignedEncryptedPseudonymMigrationExchangeTest {

    @Test
    public void coverage() {
        var request = mock(OASSignedEncryptedPseudonymMigrationRequest.class);
        var exchange = new MicroprofileSignedEncryptedPseudonymMigrationExchange(request);

        var epResource = "/legacy/ep02.asn1";
        var rawInput = TestResources.resourceToByteArray(epResource);
        var mappedInput = Asn1SignedEncryptedPseudonymEnvelope.fromByteArray(rawInput);
        exchange.setMappedInput(mappedInput);
        assertEquals(mappedInput, exchange.getMappedInput());

        var migrationSourceKey = mock(PemEcPrivateKey.class);
        exchange.setMigrationSourceKey(migrationSourceKey);
        assertEquals(migrationSourceKey, exchange.getMigrationSourceKey());

        // target migrant / target migration key set version
        assertFalse(exchange.isMigrationTargetSelectionInvalid());
        var targetMigrant = TestValues.anyString();
        when(request.getTargetMigrant()).thenReturn(targetMigrant);
        assertEquals(targetMigrant, exchange.getRequestTargetMigrant());

        var targetMigrantKeySetVersion = String.valueOf(TestValues.randomLong());
        assertTrue(exchange.isMigrationTargetSelectionInvalid());
        when(request.getTargetMigrantKeySetVersion()).thenReturn(targetMigrantKeySetVersion);
        assertEquals(targetMigrantKeySetVersion, exchange.getRequestTargetMigrantKeySetVersion().toString());

        assertFalse(exchange.isMigrationTargetSelectionInvalid());

        // other
        assertEquals(exchange, exchange.getVerificationKeys());

        // migration id
        var migrationId = TestValues.anyString();
        when(request.getMigrationID()).thenReturn(migrationId);
        assertEquals(migrationId, exchange.getRequestMigrationId());

        // scheme key
        assertFalse(exchange.isMatchingSchemeKeyUrn(""));
    }

}



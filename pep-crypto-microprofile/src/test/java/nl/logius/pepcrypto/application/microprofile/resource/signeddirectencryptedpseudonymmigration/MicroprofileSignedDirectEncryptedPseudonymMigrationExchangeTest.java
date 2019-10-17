package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedpseudonymmigration;

import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymMigrationRequest;
import nl.logius.pepcrypto.lib.TestResources;
import nl.logius.pepcrypto.lib.TestValues;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MicroprofileSignedDirectEncryptedPseudonymMigrationExchangeTest {

    @Test
    public void coverage() {
        var request = mock(OASSignedDirectEncryptedPseudonymMigrationRequest.class);
        var exchange = new MicroprofileSignedDirectEncryptedPseudonymMigrationExchange(request);

        var depResource = "/v1/happy_flow/dep02.asn1";
        var rawInput = TestResources.resourceToByteArray(depResource);
        var mappedInput = Asn1SignedDirectEncryptedPseudonymEnvelope.fromByteArray(rawInput);
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



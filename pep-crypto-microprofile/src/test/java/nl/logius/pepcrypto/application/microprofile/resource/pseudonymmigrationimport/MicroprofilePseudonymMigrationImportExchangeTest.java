package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationimport;

import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationImportRequest;
import nl.logius.pepcrypto.lib.TestResources;
import nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym;
import nl.logius.pepcrypto.lib.pem.PemEcPrivateKey;
import org.bouncycastle.math.ec.ECPoint;
import org.junit.Test;

import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.TARGET_MIGRANT;
import static nl.logius.pepcrypto.lib.pem.PemEcPrivateKeyHeader.TARGET_MIGRANT_KEY_SET_VERSION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class MicroprofilePseudonymMigrationImportExchangeTest {

    @Test
    public void coverage() {
        var request = mock(OASPseudonymMigrationImportRequest.class);
        var exchange = new MicroprofilePseudonymMigrationImportExchange(request);

        var epResource = "/v1/pseudonyms/mip_99990020000000000001_20180420_to_99990020000000000001_20211207_from_21_to_22.asn1";
        var rawInput = TestResources.resourceToByteArray(epResource);
        var mappedInput = Asn1MigrationIntermediaryPseudonym.fromByteArray(rawInput);
        exchange.setMappedInput(mappedInput);
        assertEquals(mappedInput, exchange.getMappedInput());

        assertNotNull(exchange.getEcPoint());

        var ecPoint = mock(ECPoint.class);
        exchange.setConvertedEcPoint(ecPoint);
        assertSame(ecPoint, exchange.getDecryptedPseudonymResultEcPoint());

        var migrationTargeKey = mock(PemEcPrivateKey.class);
        exchange.setMigrationTargetKey(migrationTargeKey);
        exchange.getSelectedConversionKey();

        assertSame(mappedInput, exchange.getDecryptedPseudonymResultAsn1Pseudonym());

        doReturn("recipient").when(migrationTargeKey).getSpecifiedHeader(TARGET_MIGRANT);
        doReturn("1").when(migrationTargeKey).getSpecifiedHeader(TARGET_MIGRANT_KEY_SET_VERSION);

        exchange.getDecryptedPseudonymResultPepRecipientKeyId();
    }

}



package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationexport;

import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;
import nl.logius.pepcrypto.lib.crypto.PepCrypto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static java.math.BigInteger.TEN;
import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofilePseudonymMigrationExportResponseMapperTest {

    @InjectMocks
    private MicroprofilePseudonymMigrationExportResponseMapper mapper;

    @Test
    public void mapToResponse() {
        var exchange = mock(MicroprofilePseudonymMigrationExportExchange.class);
        var ecPoint = PepCrypto.getBasePoint();

        //  mappedInput
        var rawInput = resourceToByteArray("/v1/pseudonyms/p01_div1.asn1");
        var mappedInput = Asn1DecryptedPseudonym.fromByteArray(rawInput);
        when(exchange.getMappedInput()).thenReturn(mappedInput);

        when(exchange.getMigrationSourceTargetMigrant()).thenReturn("sourceTargetMigrant");
        when(exchange.getMigrationSourceTargetKeySetVersion()).thenReturn(TEN);
        when(exchange.getMigrationSourceMigrationId()).thenReturn("migrationID");
        when(exchange.getConvertedEcPoint()).thenReturn(ecPoint);

        mapper.mapToResponse(exchange);
    }

}


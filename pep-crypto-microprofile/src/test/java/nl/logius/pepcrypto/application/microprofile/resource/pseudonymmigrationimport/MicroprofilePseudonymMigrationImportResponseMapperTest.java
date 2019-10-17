package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationimport;

import nl.logius.pepcrypto.lib.asn1.decryptedpseudonym.Asn1DecryptedPseudonym;
import nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static nl.logius.pepcrypto.lib.TestResources.resourceToByteArray;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofilePseudonymMigrationImportResponseMapperTest {

    @InjectMocks
    private MicroprofilePseudonymMigrationImportResponseMapper mapper;

    @Test
    public void mapToResponse() {
        var exchange = mock(MicroprofilePseudonymMigrationImportExchange.class);

        //  mappedInput
        var rawInput = resourceToByteArray("/v1/pseudonyms/mip_99990020000000000001_20180420_to_99990020000000000001_20211207_from_21_to_22.asn1");
        var mappedInput = Asn1MigrationIntermediaryPseudonym.fromByteArray(rawInput);
        when(exchange.getMappedInput()).thenReturn(mappedInput);

        var rawInputPseudonym = resourceToByteArray("/v1/pseudonyms/p_22.asn1");
        var asn1DecryptedPseudonym = Asn1DecryptedPseudonym.fromByteArray(rawInputPseudonym);
        when(exchange.asAsn1DecryptedPseudonym()).thenReturn(asn1DecryptedPseudonym);

        mapper.mapToResponse(exchange);
    }

}


package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_3_3;

import nl.logius.pepcrypto.api.ApiAsn1Mapper;
import nl.logius.pepcrypto.api.decrypted.ApiMigrationImportExchange;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.asn1.migrationintermediarypseudonym.Asn1MigrationIntermediaryPseudonym;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_3_3;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(StrictStubs.class)
public class ApiMigrationIntermediaryPseudonymMigrationImportTest {

    @InjectMocks
    private ApiMigrationIntermediaryPseudonymMigrationImport service;

    @Mock
    @ApiOID(OID_2_16_528_1_1003_10_1_3_3)
    private ApiAsn1Mapper<Asn1MigrationIntermediaryPseudonym> asn1Mapper;

    @Test
    public void processExchange() {
        var exchange = mock(ApiMigrationImportExchange.class);

        service.processExchange(exchange);

        verify(asn1Mapper).processRawInput(exchange);
        verify(exchange).convert();
        verifyNoMoreInteractions(exchange);
    }

}

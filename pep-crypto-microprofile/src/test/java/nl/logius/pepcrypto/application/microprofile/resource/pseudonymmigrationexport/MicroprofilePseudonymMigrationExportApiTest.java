package nl.logius.pepcrypto.application.microprofile.resource.pseudonymmigrationexport;

import generated.nl.logius.pepcrypto.openapi.model.OASMigrationIntermediaryPseudonymResponse;
import generated.nl.logius.pepcrypto.openapi.model.OASPseudonymMigrationExportRequest;
import nl.logius.pepcrypto.api.ApiAsn1SequenceDecodable;
import nl.logius.pepcrypto.api.decrypted.ApiMigrationExportService;
import nl.logius.pepcrypto.application.microprofile.oid.MicroprofileAsn1SequenceDecoder;
import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import javax.enterprise.inject.Instance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofilePseudonymMigrationExportApiTest
        extends MicroprofileAsn1SequenceDecoder {

    @InjectMocks
    private MicroprofilePseudonymMigrationExportApi api;

    @Mock
    private Instance<ApiMigrationExportService> serviceInstance;

    @Mock
    private MicroprofileAsn1SequenceDecoder asn1SequenceDecoder;

    @Mock
    private MicroprofilePseudonymMigrationExportResponseMapper responseMapper;

    private String oid;

    @Override
    public void decodeRawInput(ApiAsn1SequenceDecodable decodable) {
        decodable.setOid(oid);
    }

    @Test
    public void processMigrationRequestEiUnsupportedMapping() {
        var request = mock(OASPseudonymMigrationExportRequest.class);

        try {
            api.processRequest(request);
            fail();
        } catch (PepRuntimeException cause) {
            assertEquals("OID_NOT_SUPPORTED", cause.getMessage());
        }
    }

    @Test
    public void processMigrationRequest() throws NoSuchFieldException, IllegalAccessException {
        var response = mock(OASMigrationIntermediaryPseudonymResponse.class);
        oid = "2.16.528.1.1003.10.1.3.2";

        var asn1SequenceDecoderField = MicroprofilePseudonymMigrationExportApi.class.getDeclaredField("asn1SequenceDecoder");
        asn1SequenceDecoderField.setAccessible(true);
        asn1SequenceDecoderField.set(api, this);

        // Mock the request
        var request = mock(OASPseudonymMigrationExportRequest.class);

        // Mock the Response
        when(responseMapper.mapToResponse(any())).thenReturn(response);

        var migrationExportService = mock(ApiMigrationExportService.class);
        when(serviceInstance.select(any())).thenReturn(serviceInstance);
        when(serviceInstance.get()).thenReturn(migrationExportService);

        // Perform call
        var actual = api.processRequest(request);

        assertEquals(response, actual);
    }

}

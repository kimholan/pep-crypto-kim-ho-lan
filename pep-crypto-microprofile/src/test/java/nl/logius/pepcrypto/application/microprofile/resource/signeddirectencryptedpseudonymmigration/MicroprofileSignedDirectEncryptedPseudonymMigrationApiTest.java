package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedpseudonymmigration;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedPseudonymResponse;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymMigrationRequest;
import nl.logius.pepcrypto.api.ApiAsn1SequenceDecodable;
import nl.logius.pepcrypto.api.encrypted.ApiSignedDirectEncryptedPseudonymMigrationService;
import nl.logius.pepcrypto.application.microprofile.oid.MicroprofileAsn1SequenceDecoder;
import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofileSignedDirectEncryptedPseudonymMigrationApiTest
        extends MicroprofileAsn1SequenceDecoder {

    @InjectMocks
    private MicroprofileSignedDirectEncryptedPseudonymMigrationApi api;

    @Mock
    @Any
    private Instance<ApiSignedDirectEncryptedPseudonymMigrationService> serviceInstance;

    @Mock
    private MicroprofileAsn1SequenceDecoder asn1SequenceDecoder;

    @Mock
    private MicroprofileSignedDirectEncryptedPseudonymMigrationResponseMapper responseMapper;

    private String oid;

    @Override
    public void decodeRawInput(ApiAsn1SequenceDecodable decodable) {
        decodable.setOid(oid);
    }

    @Test
    public void processDirectEncryptedPseudonymRequestEiUnsupportedMapping() {
        var request = mock(OASSignedDirectEncryptedPseudonymMigrationRequest.class);

        try {
            api.processRequest(request);
            fail();
        } catch (PepRuntimeException cause) {
            assertEquals("OID_NOT_SUPPORTED", cause.getMessage());
        }
    }

    @Test
    public void processDirectEncryptedPseudonymRequest() throws NoSuchFieldException, IllegalAccessException {
        var response = mock(OASDecryptedPseudonymResponse.class);
        oid = "2.16.528.1.1003.10.1.2.6";

        var asn1SequenceDecoderField = MicroprofileSignedDirectEncryptedPseudonymMigrationApi.class.getDeclaredField("asn1SequenceDecoder");
        asn1SequenceDecoderField.setAccessible(true);
        asn1SequenceDecoderField.set(api, this);

        // Mock the Response
        when(responseMapper.mapToResponse(any())).thenReturn(response);

        // Mock the request
        var request = mock(OASSignedDirectEncryptedPseudonymMigrationRequest.class);

        var signedDirectEncryptedPseudonymService = mock(ApiSignedDirectEncryptedPseudonymMigrationService.class);
        when(serviceInstance.select(any())).thenReturn(serviceInstance);
        when(serviceInstance.get()).thenReturn(signedDirectEncryptedPseudonymService);

        // Perform call
        var actual = api.processRequest(request);

        assertEquals(response, actual);
    }

}

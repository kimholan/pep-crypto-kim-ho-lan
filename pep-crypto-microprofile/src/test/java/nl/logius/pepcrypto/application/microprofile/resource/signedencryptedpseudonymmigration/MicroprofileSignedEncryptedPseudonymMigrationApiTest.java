package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedpseudonymmigration;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedPseudonymResponse;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymMigrationRequest;
import nl.logius.pepcrypto.api.ApiAsn1SequenceDecodable;
import nl.logius.pepcrypto.api.encrypted.ApiSignedEncryptedPseudonymMigrationService;
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
public class MicroprofileSignedEncryptedPseudonymMigrationApiTest
        extends MicroprofileAsn1SequenceDecoder {

    @InjectMocks
    private MicroprofileSignedEncryptedPseudonymMigrationApi api;

    @Mock
    @Any
    private Instance<ApiSignedEncryptedPseudonymMigrationService> serviceInstance;

    @Mock
    private MicroprofileAsn1SequenceDecoder asn1SequenceDecoder;

    @Mock
    private MicroprofileSignedEncryptedPseudonymMigrationResponseMapper responseMapper;

    private String oid;

    @Override
    public void decodeRawInput(ApiAsn1SequenceDecodable decodable) {
        decodable.setOid(oid);
    }

    @Test
    public void processEncryptedPseudonymRequestEiUnsupportedMapping() {
        var request = mock(OASSignedEncryptedPseudonymMigrationRequest.class);

        try {
            api.processRequest(request);
            fail();
        } catch (PepRuntimeException cause) {
            assertEquals("OID_NOT_SUPPORTED", cause.getMessage());
        }
    }

    @Test
    public void processEncryptedPseudonymRequest() throws NoSuchFieldException, IllegalAccessException {
        var response = mock(OASDecryptedPseudonymResponse.class);
        oid = "2.16.528.1.1003.10.1.2.8";

        var asn1SequenceDecoderField = MicroprofileSignedEncryptedPseudonymMigrationApi.class.getDeclaredField("asn1SequenceDecoder");
        asn1SequenceDecoderField.setAccessible(true);
        asn1SequenceDecoderField.set(api, this);

        // Mock the Response
        when(responseMapper.mapToResponse(any())).thenReturn(response);

        // Mock the request
        var request = mock(OASSignedEncryptedPseudonymMigrationRequest.class);

        var signedEncryptedPseudonymService = mock(ApiSignedEncryptedPseudonymMigrationService.class);
        when(serviceInstance.select(any())).thenReturn(serviceInstance);
        when(serviceInstance.get()).thenReturn(signedEncryptedPseudonymService);

        // Perform call
        var actual = api.processRequest(request);

        assertEquals(response, actual);
    }

}

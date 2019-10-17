package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedidentity;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedIdentityResponse;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedIdentityRequest;
import nl.logius.pepcrypto.api.ApiAsn1SequenceDecodable;
import nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_7.ApiSignedEncryptedIdentityDecryption;
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
public class MicroprofileSignedEncryptedIdentityApiTest
        extends MicroprofileAsn1SequenceDecoder {

    @InjectMocks
    private MicroprofileSignedEncryptedIdentityApi api;

    @Mock
    private Instance<ApiSignedEncryptedIdentityDecryption> serviceInstance;

    @Mock
    private MicroprofileAsn1SequenceDecoder asn1SequenceDecoder;

    @Mock
    private MicroprofileSignedEncryptedIdentityResponseMapper responseMapper;

    private String oid;

    @Override
    public void decodeRawInput(ApiAsn1SequenceDecodable decodable) {
        decodable.setOid(oid);
    }

    @Test
    public void processEncryptedIdentityRequestEiUnsupportedMapping() {
        var request = mock(OASSignedEncryptedIdentityRequest.class);

        try {
            api.processRequest(request);
            fail();
        } catch (PepRuntimeException cause) {
            assertEquals("OID_NOT_SUPPORTED", cause.getMessage());
        }
    }

    @Test
    public void processEncryptedIdentityRequest() throws NoSuchFieldException, IllegalAccessException {
        var response = mock(OASDecryptedIdentityResponse.class);
        oid = "2.16.528.1.1003.10.1.2.3";

        var asn1SequenceDecoderField = MicroprofileSignedEncryptedIdentityApi.class.getDeclaredField("asn1SequenceDecoder");
        asn1SequenceDecoderField.setAccessible(true);
        asn1SequenceDecoderField.set(api, this);

        // Mock the Response
        when(responseMapper.mapToResponse(any())).thenReturn(response);

        // Mock the request
        var request = mock(OASSignedEncryptedIdentityRequest.class);

        var signedEncryptedIdentityService = mock(ApiSignedEncryptedIdentityDecryption.class);
        when(serviceInstance.select(any())).thenReturn(serviceInstance);
        when(serviceInstance.get()).thenReturn(signedEncryptedIdentityService);

        // Perform call
        var actual = api.processRequest(request);

        assertEquals(response, actual);
    }

}

package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedidentity;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedIdentityResponse;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedIdentityRequest;
import nl.logius.pepcrypto.api.ApiAsn1SequenceDecodable;
import nl.logius.pepcrypto.api.encrypted.ApiSignedDirectEncryptedIdentityService;
import nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_10_2.ApiSignedDirectEncryptedIdentityv2Decryption;
import nl.logius.pepcrypto.application.microprofile.oid.MicroprofileAsn1SequenceDecoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import javax.enterprise.inject.Instance;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class MicroprofileSignedDirectEncryptedIdentityApiTest
        extends MicroprofileAsn1SequenceDecoder {

    @InjectMocks
    private MicroprofileSignedDirectEncryptedIdentityApi api;

    @Mock
    private Instance<ApiSignedDirectEncryptedIdentityService> serviceInstance;

    @Mock
    private MicroprofileAsn1SequenceDecoder asn1SequenceDecoder;

    @Mock
    private MicroprofileSignedDirectEncryptedIdentityResponseMapper responseMapper;

    private String oid;

    @Override
    public void decodeRawInput(ApiAsn1SequenceDecodable decodable) {
        decodable.setOid(oid);
    }

    @Test
    public void processEncryptedIdentityRequest() throws NoSuchFieldException, IllegalAccessException {
        var response = mock(OASDecryptedIdentityResponse.class);
        oid = "2.16.528.1.1003.10.1.2.10.2";

        var asn1SequenceDecoderField = MicroprofileSignedDirectEncryptedIdentityApi.class
                                               .getDeclaredField("asn1SequenceDecoder");
        asn1SequenceDecoderField.setAccessible(true);
        asn1SequenceDecoderField.set(api, this);

        // Mock the request
        var request = mock(OASSignedDirectEncryptedIdentityRequest.class);

        // Mock the Response
        when(responseMapper.mapToResponse(any())).thenReturn(response);

        var signedEncryptedIdentityService = mock(ApiSignedDirectEncryptedIdentityv2Decryption.class);
        when(serviceInstance.select(any())).thenReturn(serviceInstance);
        when(serviceInstance.get()).thenReturn(signedEncryptedIdentityService);

        // Perform call
        var actual = api.processRequest(request);

        assertEquals(response, actual);
    }

}




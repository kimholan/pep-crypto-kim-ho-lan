package nl.logius.pepcrypto.application.microprofile.resource.signedencryptedpseudonym;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedPseudonymResponse;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedEncryptedPseudonymRequest;
import nl.logius.pepcrypto.api.ApiAsn1SequenceDecodable;
import nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_8.ApiSignedEncryptedPseudonymDecryption;
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
public class MicroprofileSignedEncryptedPseudonymApiTest
        extends MicroprofileAsn1SequenceDecoder {

    @InjectMocks
    private MicroprofileSignedEncryptedPseudonymApi api;

    @Mock
    private Instance<ApiSignedEncryptedPseudonymDecryption> serviceInstance;

    @Mock
    private MicroprofileAsn1SequenceDecoder asn1SequenceDecoder;

    @Mock
    private MicroprofileSignedEncryptedPseudonymResponseMapper responseMapper;

    private String oid;

    @Override
    public void decodeRawInput(ApiAsn1SequenceDecodable decodable) {
        decodable.setOid(oid);
    }

    @Test
    public void processEncryptedPseudonymRequestEiUnsupportedMapping() {
        var request = mock(OASSignedEncryptedPseudonymRequest.class);

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

        var asn1SequenceDecoderField = MicroprofileSignedEncryptedPseudonymApi.class.getDeclaredField("asn1SequenceDecoder");
        asn1SequenceDecoderField.setAccessible(true);
        asn1SequenceDecoderField.set(api, this);

        // Mock the Response
        when(responseMapper.mapToResponse(any())).thenReturn(response);

        // Mock the request
        var request = mock(OASSignedEncryptedPseudonymRequest.class);

        var signedEncryptedPseudonymService = mock(ApiSignedEncryptedPseudonymDecryption.class);
        when(serviceInstance.select(any())).thenReturn(serviceInstance);
        when(serviceInstance.get()).thenReturn(signedEncryptedPseudonymService);

        // Perform call
        var actual = api.processRequest(request);

        assertEquals(response, actual);
    }

}

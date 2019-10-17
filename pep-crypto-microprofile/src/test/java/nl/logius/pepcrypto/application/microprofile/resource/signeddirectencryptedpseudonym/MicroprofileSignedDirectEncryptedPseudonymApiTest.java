package nl.logius.pepcrypto.application.microprofile.resource.signeddirectencryptedpseudonym;

import generated.nl.logius.pepcrypto.openapi.model.OASDecryptedPseudonymResponse;
import generated.nl.logius.pepcrypto.openapi.model.OASSignedDirectEncryptedPseudonymRequest;
import nl.logius.pepcrypto.api.ApiAsn1SequenceDecodable;
import nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_6.ApiSignedDirectEncryptedPseudonymDecryption;
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
public class MicroprofileSignedDirectEncryptedPseudonymApiTest
        extends MicroprofileAsn1SequenceDecoder {

    @InjectMocks
    private MicroprofileSignedDirectEncryptedPseudonymApi api;

    @Mock
    private Instance<ApiSignedDirectEncryptedPseudonymDecryption> serviceInstance;

    @Mock
    private MicroprofileAsn1SequenceDecoder asn1SequenceDecoder;

    @Mock
    private MicroprofileSignedDirectEncryptedPseudonymResponseMapper responseMapper;

    private String oid;

    @Override
    public void decodeRawInput(ApiAsn1SequenceDecodable decodable) {
        decodable.setOid(oid);
    }

    @Test
    public void processEncryptedPseudonymRequestEiUnsupportedMapping() {
        var request = mock(OASSignedDirectEncryptedPseudonymRequest.class);

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
        oid = "2.16.528.1.1003.10.1.2.6";

        var asn1SequenceDecoderField = MicroprofileSignedDirectEncryptedPseudonymApi.class
                                               .getDeclaredField("asn1SequenceDecoder");
        asn1SequenceDecoderField.setAccessible(true);
        asn1SequenceDecoderField.set(api, this);

        // Mock the request
        var request = mock(OASSignedDirectEncryptedPseudonymRequest.class);

        // Mock the Response
        when(responseMapper.mapToResponse(any())).thenReturn(response);

        var signedEncryptedPseudonymService = mock(ApiSignedDirectEncryptedPseudonymDecryption.class);
        when(serviceInstance.select(any())).thenReturn(serviceInstance);
        when(serviceInstance.get()).thenReturn(signedEncryptedPseudonymService);

        // Perform call
        var actual = api.processRequest(request);

        assertEquals(response, actual);
    }

}

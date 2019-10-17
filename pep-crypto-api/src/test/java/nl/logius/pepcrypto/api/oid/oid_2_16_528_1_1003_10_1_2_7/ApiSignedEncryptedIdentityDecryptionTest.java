package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_7;

import nl.logius.pepcrypto.api.ApiAsn1Mapper;
import nl.logius.pepcrypto.api.ApiDecryptable;
import nl.logius.pepcrypto.api.ApiDecryption;
import nl.logius.pepcrypto.api.ApiSignatureVerifier;
import nl.logius.pepcrypto.api.encrypted.ApiSignedEncryptedIdentityExchange;
import nl.logius.pepcrypto.api.oid.ApiOID;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_0_4_0_127_0_7_1_1_4_4_3;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_1;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_7;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(StrictStubs.class)
public class ApiSignedEncryptedIdentityDecryptionTest {

    @InjectMocks
    private ApiSignedEncryptedIdentityDecryption service;

    @Mock
    @ApiOID(OID_2_16_528_1_1003_10_1_2_7)
    private ApiAsn1Mapper asn1Mapper;

    @Mock
    @ApiOID(OID_0_4_0_127_0_7_1_1_4_4_3)
    private ApiSignatureVerifier signatureVerifier;

    @Mock
    @ApiOID(OID_2_16_528_1_1003_10_1_2_1)
    private ApiDecryption<ApiDecryptable> signedEncryptedIdentity;

    @After
    public void after() {
        verifyNoMoreInteractions(asn1Mapper, signatureVerifier, signedEncryptedIdentity);
    }

    @Test
    public void decrypt() {
        var exchange = mock(ApiSignedEncryptedIdentityExchange.class);

        service.processExchange(exchange);

        verify(asn1Mapper).processRawInput(exchange);
        verify(signatureVerifier).verify(exchange);
        verify(signedEncryptedIdentity).processDecryption(exchange);
    }

}

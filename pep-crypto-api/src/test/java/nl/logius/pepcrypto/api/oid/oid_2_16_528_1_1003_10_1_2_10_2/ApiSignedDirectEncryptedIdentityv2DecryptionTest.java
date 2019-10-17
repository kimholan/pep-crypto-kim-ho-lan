package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_10_2;

import nl.logius.pepcrypto.api.ApiAsn1Mapper;
import nl.logius.pepcrypto.api.ApiDecryption;
import nl.logius.pepcrypto.api.ApiDirectEncryptedIdentityDecryptable;
import nl.logius.pepcrypto.api.ApiSignatureVerifier;
import nl.logius.pepcrypto.api.encrypted.ApiSignedDirectEncryptedIdentityExchange;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope;
import nl.logius.pepcrypto.lib.crypto.PepPublicVerificationKey;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_1_2_840_10045_4_3_3;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_10_2;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_9_2;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(StrictStubs.class)
public class ApiSignedDirectEncryptedIdentityv2DecryptionTest {

    @InjectMocks
    private ApiSignedDirectEncryptedIdentityv2Decryption service;

    @Mock
    @ApiOID(OID_2_16_528_1_1003_10_1_2_10_2)
    private ApiAsn1Mapper<Asn1SignedDirectEncryptedIdentityv2Envelope> asn1Mapper;

    @Mock
    @ApiOID(OID_1_2_840_10045_4_3_3)
    private ApiSignatureVerifier<PepPublicVerificationKey> signatureVerifier;

    @Mock
    @ApiOID(OID_2_16_528_1_1003_10_1_2_9_2)
    private ApiDecryption<ApiDirectEncryptedIdentityDecryptable> directEncryptedIdentity;

    @After
    public void after() {
        verifyNoMoreInteractions(asn1Mapper, signatureVerifier, directEncryptedIdentity);
    }

    @Test
    public void decrypt() {
        var exchange = mock(ApiSignedDirectEncryptedIdentityExchange.class);

        service.processExchange(exchange);

        verify(asn1Mapper).processRawInput(exchange);
        verify(signatureVerifier).verify(exchange);
        verify(directEncryptedIdentity).processDecryption(exchange);
    }

}

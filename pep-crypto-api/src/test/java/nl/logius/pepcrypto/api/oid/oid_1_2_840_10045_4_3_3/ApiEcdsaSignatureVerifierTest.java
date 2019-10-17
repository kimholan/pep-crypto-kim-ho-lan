package nl.logius.pepcrypto.api.oid.oid_1_2_840_10045_4_3_3;

import nl.logius.pepcrypto.api.ApiSignatureVerifiable;
import nl.logius.pepcrypto.lib.TestResources;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerificationKey;
import nl.logius.pepcrypto.lib.lang.PepRuntimeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.INVALID_SIGNATURE_VALUE;
import static nl.logius.pepcrypto.lib.TestPemObjects.readSchemePublicKeyValueFromResource;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class ApiEcdsaSignatureVerifierTest {

    @InjectMocks
    private ApiEcdsaSignatureVerifier verifier;

    @Test
    public void verify() {
        var rawInput = TestResources.resourceToByteArray("/v1/happy_flow/dep12.asn1");
        var mapped = Asn1SignedDirectEncryptedPseudonymEnvelope.fromByteArray(rawInput);
        var verifiable = mock(ApiSignatureVerifiable.class);

        // Scheme keys
        var schemePublic = readSchemePublicKeyValueFromResource("/v1/keys/u_sksv_1_kv_1.pem");

        // Verification keys
        var verificationKeys = mock(PepEcSchnorrVerificationKey.class);
        when(verifiable.getVerificationKeys()).thenReturn(verificationKeys);
        when(verificationKeys.getSchemePublicKey()).thenReturn(schemePublic);

        // Signed data
        when(verifiable.getSignedData()).thenReturn(mapped.asn1SignedData());

        // EC-Signature
        when(verifiable.getSignature()).thenReturn(mapped.asn1Signature());

        verifier.verify(verifiable);
    }

    @Test
    public void verifyFailed() {
        var rawInput = TestResources.resourceToByteArray("/v1/happy_flow/dep12.asn1");
        var mapped = Asn1SignedDirectEncryptedPseudonymEnvelope.fromByteArray(rawInput);
        var verifiable = mock(ApiSignatureVerifiable.class);

        // Scheme keys
        var schemePublic = readSchemePublicKeyValueFromResource("/v1/keys/u_sksv_10_kv_10.pem");

        // Verification keys
        var verificationKeys = mock(PepEcSchnorrVerificationKey.class);
        when(verifiable.getVerificationKeys()).thenReturn(verificationKeys);
        when(verificationKeys.getSchemePublicKey()).thenReturn(schemePublic);

        // Signed data
        when(verifiable.getSignedData()).thenReturn(mapped.asn1SignedData());

        // EC-Signature
        when(verifiable.getSignature()).thenReturn(mapped.asn1Signature());

        try {
            verifier.verify(verifiable);
            fail();
        } catch (PepRuntimeException cause) {
            assertTrue(cause.isExceptionDetail(INVALID_SIGNATURE_VALUE));
        }
    }

}

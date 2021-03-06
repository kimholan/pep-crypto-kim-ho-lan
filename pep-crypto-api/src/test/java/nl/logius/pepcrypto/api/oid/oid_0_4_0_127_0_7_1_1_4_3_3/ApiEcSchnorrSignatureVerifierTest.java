package nl.logius.pepcrypto.api.oid.oid_0_4_0_127_0_7_1_1_4_3_3;

import nl.logius.pepcrypto.api.ApiSignatureVerifiable;
import nl.logius.pepcrypto.lib.TestResources;
import nl.logius.pepcrypto.lib.asn1.signedencryptedidentity.Asn1DeprecatedSignedEncryptedIdentityEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerificationKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner.StrictStubs;

import static nl.logius.pepcrypto.lib.TestPemObjects.readPublicKeyValueFromResource;
import static nl.logius.pepcrypto.lib.TestPemObjects.readSchemePublicKeyValueFromResource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(StrictStubs.class)
public class ApiEcSchnorrSignatureVerifierTest {

    @InjectMocks
    private ApiEcSchnorrSignatureVerifier verifier;

    @Test
    public void verify() {
        var rawInput = TestResources.resourceToByteArray("/v1/happy_flow/ei04.asn1");
        var mapped = Asn1DeprecatedSignedEncryptedIdentityEnvelope.fromByteArray(rawInput);
        var verifiable = mock(ApiSignatureVerifiable.class);

        // Scheme keys
        var schemePublic = readSchemePublicKeyValueFromResource("/v1/keys/ipp_sksv_1_kv_1.pem");

        // Recipient public key
        var recipientPublicKey = readPublicKeyValueFromResource("/v1/keys/idd_sksv_1_oin_99990020000000000001_kv_20180420.pem");

        // Verification keys
        var verificationKeys = mock(PepEcSchnorrVerificationKey.class);
        when(verifiable.getVerificationKeys()).thenReturn(verificationKeys);
        when(verificationKeys.getRecipientPublicKey()).thenReturn(recipientPublicKey);
        when(verificationKeys.getSchemePublicKey()).thenReturn(schemePublic);

        // Signed data
        when(verifiable.getSignedData()).thenReturn(mapped.asn1SignedData());

        // EC-Signature
        when(verifiable.getSignature()).thenReturn(mapped.asn1Signature());

        verifier.verify(verifiable);
    }

}



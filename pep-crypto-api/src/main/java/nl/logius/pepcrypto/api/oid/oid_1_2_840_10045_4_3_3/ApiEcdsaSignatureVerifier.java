package nl.logius.pepcrypto.api.oid.oid_1_2_840_10045_4_3_3;

import nl.logius.pepcrypto.api.ApiSignatureVerifiable;
import nl.logius.pepcrypto.api.ApiSignatureVerifier;
import nl.logius.pepcrypto.api.event.ApiEventSource;
import nl.logius.pepcrypto.api.exception.ApiExceptionDetailMapper;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.crypto.PepEcdsaVerification;
import nl.logius.pepcrypto.lib.crypto.PepPublicVerificationKey;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.SIGNATURE_VERIFICATION;
import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.INVALID_SIGNATURE_VALUE;
import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.SIGNATURE_VERIFICATION_FAILED;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_1_2_840_10045_4_3_3;

/**
 * ECDSA verification.
 */
@ApplicationScoped
@ApiOID(OID_1_2_840_10045_4_3_3)
public class ApiEcdsaSignatureVerifier
        implements ApiSignatureVerifier<PepPublicVerificationKey> {

    @Override
    @ApiEventSource(SIGNATURE_VERIFICATION)
    @ApiExceptionDetailMapper(SIGNATURE_VERIFICATION_FAILED)
    public void verify(ApiSignatureVerifiable<PepPublicVerificationKey> verifiable) {
        var verificationKeys = verifiable.getVerificationKeys();
        var signedData = verifiable.getSignedData();
        var signature = verifiable.getSignature();
        var verified = PepEcdsaVerification.verify(signedData, verificationKeys, signature);

        INVALID_SIGNATURE_VALUE.requireTrue(verified);
    }

}



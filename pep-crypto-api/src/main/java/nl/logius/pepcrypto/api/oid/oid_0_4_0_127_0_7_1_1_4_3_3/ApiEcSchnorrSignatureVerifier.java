package nl.logius.pepcrypto.api.oid.oid_0_4_0_127_0_7_1_1_4_3_3;

import nl.logius.pepcrypto.api.ApiSignatureVerifiable;
import nl.logius.pepcrypto.api.ApiSignatureVerifier;
import nl.logius.pepcrypto.api.event.ApiEventSource;
import nl.logius.pepcrypto.api.exception.ApiExceptionDetailMapper;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerification;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerificationKey;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.SIGNATURE_VERIFICATION;
import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.INVALID_SIGNATURE_VALUE;
import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.SIGNATURE_VERIFICATION_FAILED;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_0_4_0_127_0_7_1_1_4_3_3;

/**
 * EC-Schnorr - Deprecated.
 */
@ApplicationScoped
@ApiOID(OID_0_4_0_127_0_7_1_1_4_3_3)
public class ApiEcSchnorrSignatureVerifier
        implements ApiSignatureVerifier<PepEcSchnorrVerificationKey> {

    @Override
    @ApiEventSource(SIGNATURE_VERIFICATION)
    @ApiExceptionDetailMapper(SIGNATURE_VERIFICATION_FAILED)
    public void verify(ApiSignatureVerifiable<PepEcSchnorrVerificationKey> verifiable) {
        var verificationKeys = verifiable.getVerificationKeys();
        var signedData = verifiable.getSignedData();
        var signature = verifiable.getSignature();
        var verified = PepEcSchnorrVerification.verify(signedData, verificationKeys, signature);

        INVALID_SIGNATURE_VALUE.requireTrue(verified);
    }

}



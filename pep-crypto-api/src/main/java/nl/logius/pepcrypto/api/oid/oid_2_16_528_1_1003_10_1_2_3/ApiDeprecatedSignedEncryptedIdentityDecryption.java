package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_3;

import nl.logius.pepcrypto.api.ApiAsn1Mapper;
import nl.logius.pepcrypto.api.ApiDecryption;
import nl.logius.pepcrypto.api.ApiEncryptedIdentityDecryptable;
import nl.logius.pepcrypto.api.ApiSignatureVerifier;
import nl.logius.pepcrypto.api.encrypted.ApiSignedEncryptedIdentityExchange;
import nl.logius.pepcrypto.api.encrypted.ApiSignedEncryptedIdentityService;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerificationKey;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_0_4_0_127_0_7_1_1_4_3_3;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_1;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_3;

/**
 * (Deprecated) DeprecatedSignedEncryptedIdentity - EC-Schnorr.
 */
@ApplicationScoped
@ApiOID(OID_2_16_528_1_1003_10_1_2_3)
public class ApiDeprecatedSignedEncryptedIdentityDecryption
        implements ApiSignedEncryptedIdentityService {

    @Inject
    @ApiOID(OID_2_16_528_1_1003_10_1_2_3)
    private ApiAsn1Mapper<Asn1IdentityEnvelope> asn1Mapper;

    @Inject
    @ApiOID(OID_0_4_0_127_0_7_1_1_4_3_3)
    private ApiSignatureVerifier<PepEcSchnorrVerificationKey> signatureVerifier;

    @Inject
    @ApiOID(OID_2_16_528_1_1003_10_1_2_1)
    private ApiDecryption<ApiEncryptedIdentityDecryptable> signedEncryptedIdentity;

    @Override
    public void processExchange(ApiSignedEncryptedIdentityExchange exchange) {
        asn1Mapper.processRawInput(exchange);

        // Verify data before decryption
        signatureVerifier.verify(exchange);

        // Decryption
        signedEncryptedIdentity.processDecryption(exchange);
    }

}

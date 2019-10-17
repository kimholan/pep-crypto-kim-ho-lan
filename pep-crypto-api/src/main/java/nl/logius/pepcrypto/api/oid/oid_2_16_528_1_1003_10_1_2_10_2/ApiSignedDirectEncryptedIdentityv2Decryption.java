package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_10_2;

import nl.logius.pepcrypto.api.ApiAsn1Mapper;
import nl.logius.pepcrypto.api.ApiDecryption;
import nl.logius.pepcrypto.api.ApiDirectEncryptedIdentityDecryptable;
import nl.logius.pepcrypto.api.ApiSignatureVerifier;
import nl.logius.pepcrypto.api.encrypted.ApiSignedDirectEncryptedIdentityExchange;
import nl.logius.pepcrypto.api.encrypted.ApiSignedDirectEncryptedIdentityService;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope;
import nl.logius.pepcrypto.lib.crypto.PepPublicVerificationKey;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_1_2_840_10045_4_3_3;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_10_2;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_9_2;

/**
 * SignedDirectEncryptedIdentity.
 */
@ApplicationScoped
@ApiOID(OID_2_16_528_1_1003_10_1_2_10_2)
public class ApiSignedDirectEncryptedIdentityv2Decryption
        implements ApiSignedDirectEncryptedIdentityService {

    @Inject
    @ApiOID(OID_2_16_528_1_1003_10_1_2_10_2)
    private ApiAsn1Mapper<Asn1SignedDirectEncryptedIdentityv2Envelope> asn1Mapper;

    @Inject
    @ApiOID(OID_1_2_840_10045_4_3_3)
    private ApiSignatureVerifier<PepPublicVerificationKey> signatureVerifier;

    @Inject
    @ApiOID(OID_2_16_528_1_1003_10_1_2_9_2)
    private ApiDecryption<ApiDirectEncryptedIdentityDecryptable> directEncryptedIdentity;

    @Override
    public void processExchange(ApiSignedDirectEncryptedIdentityExchange exchange) {
        asn1Mapper.processRawInput(exchange);

        // Verify data before decryption
        signatureVerifier.verify(exchange);

        // Decrypt
        directEncryptedIdentity.processDecryption(exchange);
    }

}

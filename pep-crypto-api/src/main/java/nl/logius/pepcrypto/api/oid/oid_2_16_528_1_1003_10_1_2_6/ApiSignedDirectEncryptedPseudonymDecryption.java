package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_6;

import nl.logius.pepcrypto.api.ApiAsn1Mapper;
import nl.logius.pepcrypto.api.ApiDecryption;
import nl.logius.pepcrypto.api.ApiDirectEncryptedPseudonymDecryptable;
import nl.logius.pepcrypto.api.ApiSignatureVerifier;
import nl.logius.pepcrypto.api.encrypted.ApiSignedDirectEncryptedPseudonymExchange;
import nl.logius.pepcrypto.api.encrypted.ApiSignedDirectEncryptedPseudonymService;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedpseudonym.Asn1SignedDirectEncryptedPseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepPublicVerificationKey;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_1_2_840_10045_4_3_3;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_5;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_6;

/**
 * SignedDirectEncryptedPseudonym.
 */
@ApplicationScoped
@ApiOID(OID_2_16_528_1_1003_10_1_2_6)
public class ApiSignedDirectEncryptedPseudonymDecryption
        implements ApiSignedDirectEncryptedPseudonymService {

    @Inject
    @ApiOID(OID_2_16_528_1_1003_10_1_2_6)
    private ApiAsn1Mapper<Asn1SignedDirectEncryptedPseudonymEnvelope> asn1Mapper;

    @Inject
    @ApiOID(OID_1_2_840_10045_4_3_3)
    private ApiSignatureVerifier<PepPublicVerificationKey> signatureVerifier;

    @Inject
    @ApiOID(OID_2_16_528_1_1003_10_1_2_5)
    private ApiDecryption<ApiDirectEncryptedPseudonymDecryptable> directEncryptedPseudonym;

    @Override
    public void processExchange(ApiSignedDirectEncryptedPseudonymExchange exchange) {
        asn1Mapper.processRawInput(exchange);

        // Verify data before decryption
        signatureVerifier.verify(exchange);

        // Decrypt
        directEncryptedPseudonym.processDecryption(exchange);
    }

}

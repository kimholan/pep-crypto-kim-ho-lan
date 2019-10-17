package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_8;

import nl.logius.pepcrypto.api.ApiAsn1Mapper;
import nl.logius.pepcrypto.api.ApiDecryption;
import nl.logius.pepcrypto.api.ApiEncryptedPseudonymMigratable;
import nl.logius.pepcrypto.api.ApiSignatureVerifier;
import nl.logius.pepcrypto.api.encrypted.ApiSignedEncryptedPseudonymMigrationExchange;
import nl.logius.pepcrypto.api.encrypted.ApiSignedEncryptedPseudonymMigrationService;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.asn1.Asn1PseudonymEnvelope;
import nl.logius.pepcrypto.lib.crypto.PepEcSchnorrVerificationKey;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_0_4_0_127_0_7_1_1_4_4_3;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_2;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_8;

/**
 * SignedEncryptedPseudonym - EC-Schnorr Digital Signature Algorithm (ECSDSA).
 */
@ApplicationScoped
@ApiOID(OID_2_16_528_1_1003_10_1_2_8)
public class ApiSignedEncryptedPseudonymDecryptionMigration
        implements ApiSignedEncryptedPseudonymMigrationService {

    @Inject
    @ApiOID(OID_2_16_528_1_1003_10_1_2_8)
    private ApiAsn1Mapper<Asn1PseudonymEnvelope> asn1Mapper;

    @Inject
    @ApiOID(OID_0_4_0_127_0_7_1_1_4_4_3)
    private ApiSignatureVerifier<PepEcSchnorrVerificationKey> signatureVerifier;

    @Inject
    @ApiOID(OID_2_16_528_1_1003_10_1_2_2)
    private ApiDecryption<ApiEncryptedPseudonymMigratable> signedEncryptedPseudonym;

    @Override
    public void processExchange(ApiSignedEncryptedPseudonymMigrationExchange exchange) {
        asn1Mapper.processRawInput(exchange);

        // Verify data before decryption
        signatureVerifier.verify(exchange);

        // Decryption
        signedEncryptedPseudonym.processDecryption(exchange);
    }

}

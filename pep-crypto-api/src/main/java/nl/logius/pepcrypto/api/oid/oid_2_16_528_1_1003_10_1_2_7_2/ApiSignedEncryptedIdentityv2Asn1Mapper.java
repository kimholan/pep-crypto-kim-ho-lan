package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_7_2;

import nl.logius.pepcrypto.api.ApiAsn1Mapper;
import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.api.event.ApiEventSource;
import nl.logius.pepcrypto.api.exception.ApiExceptionDetailMapper;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.asn1.Asn1IdentityEnvelope;
import nl.logius.pepcrypto.lib.asn1.signedencryptedidentityv2.Asn1SignedEncryptedIdentityv2Envelope;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.ASN1_MAPPING;
import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.ASN1_MAPPING_FAILED;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_7_2;

/**
 * SignedEncryptedIdentity-v2 - EC-Schnorr Digital Signature Algorithm (ECSDSA).
 */
@ApplicationScoped
@ApiOID(OID_2_16_528_1_1003_10_1_2_7_2)
public class ApiSignedEncryptedIdentityv2Asn1Mapper
        implements ApiAsn1Mapper<Asn1IdentityEnvelope> {

    @Override
    @ApiEventSource(ASN1_MAPPING)
    @ApiExceptionDetailMapper(ASN1_MAPPING_FAILED)
    public void processRawInput(ApiExchange<Asn1IdentityEnvelope> exchange) {
        var decodedAsn1Sequence = exchange.getRawInput();
        var signedEncryptedIdentity = Asn1SignedEncryptedIdentityv2Envelope.fromByteArray(decodedAsn1Sequence);

        exchange.setMappedInput(signedEncryptedIdentity);
    }

}

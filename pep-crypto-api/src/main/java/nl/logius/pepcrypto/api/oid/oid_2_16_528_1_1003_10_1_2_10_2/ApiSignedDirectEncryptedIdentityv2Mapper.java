package nl.logius.pepcrypto.api.oid.oid_2_16_528_1_1003_10_1_2_10_2;

import nl.logius.pepcrypto.api.ApiAsn1Mapper;
import nl.logius.pepcrypto.api.ApiExchange;
import nl.logius.pepcrypto.api.event.ApiEventSource;
import nl.logius.pepcrypto.api.exception.ApiExceptionDetailMapper;
import nl.logius.pepcrypto.api.oid.ApiOID;
import nl.logius.pepcrypto.lib.asn1.signeddirectencryptedidentityv2.Asn1SignedDirectEncryptedIdentityv2Envelope;

import javax.enterprise.context.ApplicationScoped;

import static nl.logius.pepcrypto.api.event.ApiEvent.ApiEventType.ASN1_MAPPING;
import static nl.logius.pepcrypto.api.exception.ApiExceptionDetail.ASN1_MAPPING_FAILED;
import static nl.logius.pepcrypto.api.oid.ApiOID.ApiOIDType.OID_2_16_528_1_1003_10_1_2_10_2;

/**
 * SignedDirectEncryptedIdentity.
 */
@ApplicationScoped
@ApiOID(OID_2_16_528_1_1003_10_1_2_10_2)
public class ApiSignedDirectEncryptedIdentityv2Mapper
        implements ApiAsn1Mapper<Asn1SignedDirectEncryptedIdentityv2Envelope> {

    @Override
    @ApiEventSource(ASN1_MAPPING)
    @ApiExceptionDetailMapper(ASN1_MAPPING_FAILED)
    public void processRawInput(ApiExchange<Asn1SignedDirectEncryptedIdentityv2Envelope> exchange) {
        var decodedAsn1Sequence = exchange.getRawInput();
        var signedDirectEncryptedIdentity = Asn1SignedDirectEncryptedIdentityv2Envelope.fromByteArray(decodedAsn1Sequence);

        exchange.setMappedInput(signedDirectEncryptedIdentity);
    }

}
